<template>
  <div class="app-container">
    <el-input v-model="filterText" placeholder="Filter keyword" style="margin-bottom:30px;" />

    
    <el-table
    :data="menuList"
    style="width: 100%;margin-bottom: 20px;"
    row-key="id"
    border
    default-expand-all
    :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
    <el-table-column
      prop="name"
      label="名称"
      sortable
      width="180">
    </el-table-column>
    <el-table-column
      prop="path"
      label="访问路径"
      sortable
      width="180">
    </el-table-column>
      <el-table-column
        prop="component"
        label="组件路径"
        sortable
        width="180">
      </el-table-column>
    <el-table-column
      prop="permissionValue"
      label="权限值">
    </el-table-column>
    <el-table-column 
      label="操作">
      <template slot-scope="scope">
      <!-- v-if="node.level == 1 || node.level == 2" v-if="node.level == 3" v-if="node.level == 4"-->
        <el-button
              v-if="(scope.row.level == 1 || scope.row.level == 2) && hasPerm('permission.add')"
              type="text"
              size="mini"
              @click="() => {dialogFormVisible = true, menu.pid = scope.row.id}">添加菜单</el-button>
            <el-button
              v-if="scope.row.level == 3 &&  hasPerm('permission.add')"
              type="text"
              size="mini"
              @click="() => {dialogPermissionVisible = true, permission.pid = scope.row.id}">添加功能</el-button>
              <el-button
              v-if="scope.row.level == 4 &&  hasPerm('permission.update')"
              type="text"
              size="mini"
              @click="() => updateFunction(scope.row)">修改功能</el-button>
            <el-button
              v-if="scope.row.level != 4 &&  hasPerm('permission.update')"
              type="text"
              size="mini"
              @click="() => getById(scope.row)">修改</el-button>
            <el-button
              type="text"
              size="mini"
              @click="() => remove(scope.row)" v-if="hasPerm('permission.remove')">删除</el-button>
        </template>
    </el-table-column>
  </el-table>

    <el-dialog :visible.sync="dialogFormVisible" :title="dialogFormValue">
        <el-form :model="menu" label-width="120px">
            <el-form-item label="菜单名称">
                <el-input v-model="menu.name"/>
            </el-form-item>
            <el-form-item label="访问路径">
            <el-input v-model="menu.path"/>
          </el-form-item>
          <el-form-item label="组件路径">
            <el-input v-model="menu.component"/>
          </el-form-item>
  
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="restData()">取 消</el-button>
            <el-button type="primary" @click="append()">确 定</el-button>
        </div>
    </el-dialog>
    <!-- 添加功能的窗口 -->
    <el-dialog :visible.sync="dialogPermissionVisible" title="添加功能">
        <el-form :model="menu" label-width="120px">
            <el-form-item label="功能名称">
                <el-input v-model="permission.name"/>
            </el-form-item>
          <el-form-item label="访问路径">
          <el-input v-model="permission.path"/>
          </el-form-item>
            <el-form-item label="组件路径">
              <el-input v-model="permission.component"/>
            </el-form-item>
            <el-form-item label="功能权限值">
                <el-input v-model="permission.permissionValue"/>
            </el-form-item>
  
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="restData()">取 消</el-button>
            <el-button type="primary" @click="appendPermission()">确 定</el-button>
        </div>
    </el-dialog>

  </div>
</template>

<script>
import menu from '@/api/acl/menu'

const menuForm = {
    name:'',
    pid: 0,
    path:'',
    component:'',
    type:'1'
}
const perForm = {
  permissionValue:'',
  type:'2',
  name:'',
  path:'',
  component:'',
  pid: 0
}

export default {

  data() {
    return {
      filterText: '',
      menuList: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      dialogFormValue:'添加菜单',
      dialogFormVisible: false,
      dialogPermissionVisible:false,
      menu: menuForm ,
      permission: perForm
    }
  },
  //监听上面文本框搜索
  watch: {
    filterText(val) {
      this.$refs.menuTree.filter(val)
    }
  },

  created() {
    this.fetchNodeList()
  },

  methods: {
    fetchNodeList() {
      menu.getNestedTreeList().then(response => {
        if (response.success === true) {
          this.menuList = response.data.children
          console.log(this.menuList)
        }
      })
    },
    filterNode(value, data) {
      if (!value) return true
      return data.title.toLowerCase().indexOf(value.toLowerCase()) !== -1
    },
    remove(data){
        console.log(data)

        this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
            return menu.removeById(data.id)
        }).then(() => {
             this.fetchNodeList()// 刷新列表
            this.$message({
                type: 'success',
                message: '删除成功!'
            })
        }).catch((response) => { // 失败
            if (response === 'cancel') {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                })
            } else {
                this.$message({
                    type: 'error',
                    message: '删除失败'
                })
            }
        })

    },
    appendPermission(){
      if(this.permission.id){
          this.update(this.permission)
      } else{
        menu.saveLevelOne(this.permission).then(response => {
            this.dialogPermissionVisible = false
            this.$message({
                type: 'success',
                message: '添加功能成功'
            })
            //刷新页面
            this.fetchNodeList()
            this.menu = {...menuForm}
            this.permission = {...perForm}
        })
      }
    },
    appendLevelOne(){
        menu.saveLevelOne(this.menu)
            .then(response => {
                this.dialogFormVisible = false
                this.$message({
                    type: 'success',
                    message: '添加一级菜单成功'
                })
                //刷新页面
                this.fetchNodeList()
                this.menu = {...menuForm}
                this.permission = {...perForm}
            })
            .catch(response => {
                //你们写：判断点击取消清空一下
                this.dialogFormVisible = false
                this.$message({
                    type: 'error',
                    message: '添加一级菜单失败'
                })
                this.menu = {...menuForm}
                this.permission = {...perForm}
            })
    },
    append(){
      if(!this.menu.id){// 添加

        if(this.menu.pid == 0 ){
            this.appendLevelOne() // 一级分类的添加
        } else{
            this.appendLevelTwo() // 二级分类的添加
        }
      } else{ //修改
        this.update(this.menu)
      }
        
    },
    update(obj){
      
      debugger
      menu.update(obj).then(response => {
        this.dialogFormVisible = false
          this.$message({
              type: 'success',
              message: '修改成功'
          })
          //刷新页面
          this.fetchNodeList()
          this.restData()
      })
    },
    appendLevelTwo(){
        menu.saveLevelOne(this.menu)
            .then(response => {
                //1、把文本框关
                this.dialogFormVisible = false
                //2、提示成功
                this.$message({
                    type:'success',
                    message:"添加二级分类成功"
                })
                //3、刷新页面
                this.fetchNodeList()
                //4、把menu清空
                this.menu = {...menuForm}
                this.permission = {...perForm}
            })
            .catch(response => {
                //1、把文本框关
                this.dialogFormVisible = false
                //2、提示成功
                this.$message({
                    type:'error',
                    message:"添加二级分类失败"
                })                
                //3、把menu清空
                this.menu = {...menuForm}
                this.permission = {...perForm}
                
            })
    },
    getById(data){
      this.dialogFormVisible = true
      this.menu = data
    },
    updateFunction(data){
      this.dialogPermissionVisible = true
      this.permission = data
    },
    restData(){
      this.dialogPermissionVisible = false
      this.dialogFormVisible = false
      this.menu = {...menuForm}
      this.permission = {...perForm}
    }
  }
}
</script>
