<template>
    <div class="app-container">
        <!-- 表单查询-->
        <el-form :inline="true" class="demo-form-inline">
         <el-form-item>
            <el-input  v-model="searchObj.hosname" placeholder="医院名称"/>
         </el-form-item>
         <el-form-item>
            <el-input v-model="searchObj.hoscode" placeholder="医院编号"/>
         </el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="getList()" plain>查询</el-button>
        </el-form>

        <!-- 工具条 -->
        <div class="button-batch-delete">
            <el-button type="danger" size="mini" @click="removeRows()" plain>批量删除</el-button>
        </div>

        <br>

        <!-- 显示列表 -->
        <el-table border style="width: 100%" :data="list" @selection-change="handleSelectionChange">
            <el-table-column type="index" width="50" label="序号"/>
            <el-table-column prop="hosname" label="医院名称"/>
            <el-table-column prop="hoscode" label="医院编号"/>
            <el-table-column prop="apiUrl" label="api基础路径" width="200"/>
            <el-table-column prop="contactsName" label="联系人姓名"/>
            <el-table-column prop="contactsPhone" label="联系人手机"/>
            <el-table-column label="状态" width="80">
                <template slot-scope="scope">
                    {{ scope.row.status === 1 ? '可用' : '不可用' }}
                </template>
            </el-table-column>
        <!-- 按钮 -->
            <el-table-column label="操作" width="280" align="center">
                <template slot-scope="scope">
                <el-button type="danger" size="mini" plain
                    icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
                <el-button v-if="scope.row.status==1" type="primary" size="mini" plain
                    icon="el-icon-lock" @click="lockHostSet(scope.row.id, 0)">锁定</el-button>
                <el-button v-if="scope.row.status==0" type="primary" size="mini" plain
                    icon="el-icon-unlock" @click="lockHostSet(scope.row.id, 1)">解锁</el-button>
                <router-link :to="'/hospSet/edit/' + scope.row.id">
                    <el-button plain type="warning" size="mini" icon="el-icon-edit-outline">修改</el-button>
                </router-link>
                </template>
            </el-table-column>
            <el-table-column type="selection" width="55"/> <!-- 选择框 -->
        </el-table>


        <!-- 分页 -->
        <el-pagination
        :current-page="current" 
        :page-size="limit"
        :total="total"
        style="padding: 30px 0; text-align: center;"
        layout="prev, pager, next"
        @current-change="getList"/>

    </div>
</template>

<script>
import hospsetApi from '@/api/hospset'

export default {
    data() {
        return {
            current: 1,
            limit: 10,
            searchObj: {
                hosname: '',
                hoscode: ''
            },
            list: [],
            total: 0,
            multipleSelection: []
        }
    },
    created() {
        this.getList()
    },
    methods: {
        getList(page = 1) {
            this.current = page 
            hospsetApi.getHospSetList(this.current, this.limit, this.searchObj)
                .then(response => {
                    this.list = response.data.records
                    this.total = response.data.total
                })
                .catch(error => {
                    console.log(error)
                })
        },
        removeDataById(id) {
            this.$confirm('此操作将永久删除该信息, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
         }).then(() => { 
            hospsetApi.deleteHospSet(id)
               .then(response => {
                  this.$message({
                     type: 'success',
                     message: '删除成功!'
                  })
                  this.getList(1)
               })
         })
        },
        removeRows() {
         this.$confirm('此操作将永久删除该信息, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
         }).then(() => { 
            var idList = []
            for(var i = 0; i < this.multipleSelection.length; i++) {
               var obj = this.multipleSelection[i]
               var id = obj.id
               idList.push(id)
            }
            hospsetApi.batchRemoveHospSet(idList)
               .then(response => {
                  this.$message({
                     type: 'success',
                     message: '删除成功!'
                  })
                  this.getList(1)
               })
         })
      },
     /* 获取选择复选框的id值 */
    handleSelectionChange(selection) {
         this.multipleSelection = selection
      },
    lockHostSet(id, status) {
         hospsetApi.lockHospSet(id, status)
            .then(response => {
               this.getList()
            })
      },
    }
}
</script>

<style>
.button-batch-delete{ 
    transform:translateX(94%);
}
</style>
