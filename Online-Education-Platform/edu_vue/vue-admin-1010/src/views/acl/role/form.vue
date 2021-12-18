<template>
  <div class="app-container">
    <el-form ref="role" :model="role" :rules="validateRules" label-width="120px">
      <el-form-item label="角色名称" prop="roleName">
        <el-input v-model="role.roleName"/>
      </el-form-item>
    <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

import roleApi from '@/api/acl/role'

const defaultForm = {
  roleName: ''
}

export default {
  data() {
    return {
      role: defaultForm,
      saveBtnDisabled: false, // 保存按钮是否禁用,
      validateRules: {
        roleName: [{ required: true, trigger: 'blur', message: '角色名必须输入' }]
      }
    }
  },

  // 监听器
  watch: {
    $route(to, from) {
      console.log('路由变化......')
      console.log(to)
      console.log(from)
      this.init()
    }
  },

  // 生命周期方法（在路由切换，组件不变的情况下不会被调用）
  created() {
    console.log('form created ......')
    this.init()
  },

  methods: {

    // 表单初始化
    init() {
      debugger
      if (this.$route.params && this.$route.params.id) {
        const id = this.$route.params.id
        this.fetchDataById(id)
      } else {
        // 对象拓展运算符：拷贝对象，而不是赋值对象的引用
        this.role = { ...defaultForm }
      }
    },

    saveOrUpdate() {
      this.$refs.role.validate(valid => {
        if (valid) {
          this.saveBtnDisabled = true // 防止表单重复提交
          if (!this.role.id) {
            this.saveData()
          } else {
            this.updateData()
          }
        }
      })
    },

    // 新增讲师
    saveData() {
      roleApi.save(this.role).then(response => {
        // debugger
        if (response.success) {
          this.$message({
            type: 'success',
            message: response.message
          })
          this.$router.push({ path: '/acl/role/list' })
        }
      })
    },

    // 根据id更新记录
    updateData() {
      // teacher数据的获取
      roleApi.updateById(this.role).then(response => {
        if (response.success) {
          this.$message({
            type: 'success',
            message: response.message
          })
          this.$router.push({ path: '/acl/role/list' })
        }
      })
    },

    // 根据id查询记录
    fetchDataById(id) {
      roleApi.getById(id).then(response => {
        debugger
        this.role = response.data.item
      })
    }

  }
}
</script>
