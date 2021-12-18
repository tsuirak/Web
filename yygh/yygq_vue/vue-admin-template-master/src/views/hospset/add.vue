<template>
  <div class="app-container">
      <el-form label-width="120px">
         <el-form-item label="医院名称">
            <el-col :span="12">
                <el-input v-model="hospitalSet.hosname" />
            </el-col>
         </el-form-item>

         <el-form-item label="医院编号">
            <el-col :span="12">
                <el-input v-model="hospitalSet.hoscode"/>
            </el-col>
         </el-form-item>

        <el-form-item label="接口路径">

            <el-col :span="12">
                <el-input placeholder="请输入接口api地址"  v-model="hospitalSet.apiUrl">
                    <template slot="prepend">http://</template>
                </el-input>
            </el-col>
        </el-form-item>

         <el-form-item label="联系人姓名">
            <el-col :span="12">
                <el-input v-model="hospitalSet.contactsName"/>  
            </el-col>
         </el-form-item>

         <el-form-item label="联系人手机">
            <el-col :span="12">
                <el-input v-model="hospitalSet.contactsPhone"/>
            </el-col>
         </el-form-item>

         <el-form-item>
            <el-col :span="12">
                <el-button type="primary" @click="saveOrUpdate">保存</el-button>
            </el-col>
         </el-form-item>
      </el-form>
   </div>
</template>
<script>
import hospset from '@/api/hospset'

export default {
   data() {
      return {
         hospitalSet: {} 
      }
   },
   created() {
      if(this.$route.params && this.$route.params.id) {
         const id = this.$route.params.id
         this.getHostSet(id)
      } else {
         this.hospitalSet = {}
      }
   },
   methods: {
      getHostSet(id) {
         hospset.getHospSet(id) 
            .then(response => {
                this.hospitalSet = response.data
                this.hospitalSet.apiUrl = this.hospitalSet.apiUrl.slice(7)
            })
      },
      save() {
        this.hospitalSet.apiUrl = "http://" + this.hospitalSet.apiUrl
        hospset.saveHospSet(this.hospitalSet)
            .then(response => {
               this.$message({
                  type: 'success',
                  message: '添加成功!'
               })
               this.$router.push({path:'/hospSet/list'})
            })
      },
      update() {
          this.hospitalSet.apiUrl = "http://" + this.hospitalSet.apiUrl
          hospset.updateHospSet(this.hospitalSet)
            .then(response => {
               this.$message({
                  type: 'success',
                  message: '修改成功!'
               })
               this.$router.push({path:'/hospSet/list'})
            })
      },
      saveOrUpdate() {
         if(!this.hospitalSet.id) { 
            this.save();
         }else {
            this.update()
         }
      }
   }
}
</script>