<template>
  <div class="app-container">
   <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="teacherQuery.name" placeholder="姓名"/>
      </el-form-item>

      <el-form-item>
        <el-select v-model="teacherQuery.level" clearable placeholder="职称">
          <el-option :value="1" label="高级教师"/>
          <el-option :value="2" label="首席教师"/>
        </el-select>
      </el-form-item>

      <el-form-item label="时间">
        <el-date-picker
          v-model="teacherQuery.begin"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="teacherQuery.end"
          type="datetime"
          placeholder="选择截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

  <!-- 中间表单 -->
  <el-table
    :data="list.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
    style="width: 100%"
    >
    <el-table-column
      label="入职日期"
      prop="gmtCreate">
    </el-table-column>
    <el-table-column
      label="姓名"
      prop="name">
    </el-table-column>
    <el-table-column
      label="简介"
      prop="intro">
    </el-table-column>
    <el-table-column
      label="学历"
      prop="career">
    </el-table-column>
    <el-table-column
      label="职称"
      prop="level">
      <template slot-scope="scope">
        {{ scope.row.level === 1 ? '高级教师':'首席教师' }}
      </template>
    </el-table-column>
    <el-table-column
      align="right">
      <template slot-scope="scope">
        <router-link :to="'/teacher/edit/'+scope.row.id">
          <el-button
          size="mini"
          icon="el-icon-edit">编辑
          </el-button>
        </router-link>
        <el-button
          size="mini"
          type="danger"
          @click="removeDataById(scope.row.id)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>

  <!-- 分页 -->
  <el-pagination
      :current-page="page"
      :page-size="limit" 
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
      >
    </el-pagination>
  </div>
</template>


<script>
import teacher from '@/api/edu/teacher'
export default {

  data() {
    return {
      page:1, // 当前页
      limit:10, // 每页记录数
      teacherQuery:{}, // 封装的对象
      list:null, // 查询后返回的集合
      total:0 // 总记录数
    }

  },
  created() {
    this.getList()
  },
  methods: {
    // 获取教师页面
    getList(page = 1) {
      this.page = page
      teacher.getTeacheListPage(this.page, this.limit, this.teacherQuery)
        .then(res => {
          console.log("=========================================================")
          console.log(res)
          this.list = res.data.data.list
          this.total = res.data.data.total
        })
        .catch(error => {
          console.log("=========================================================")
          console.log(error)
        })
    },
    resetData() {
      this.teacherQuery = {}
      this.getList()
    },
    // 删除教师信息
    removeDataById(id) {
          // 提示信息
        this.$confirm('此操作将永久删除该信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          teacher.removeTeacherById(id)
            .then(res => {
              this.$message({
                type: 'success',
                message: '删除成功'
              });
              // 刷新
              this.getList()
            })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
    }
  }
}
</script>