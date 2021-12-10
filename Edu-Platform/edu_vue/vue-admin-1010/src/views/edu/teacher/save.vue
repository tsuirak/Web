<template>
  <div class="app-container">
    <el-form label-width="80px">
      <el-form-item label="姓名">
        <el-input v-model="teacher.name"></el-input>
      </el-form-item>
      <el-form-item label="职称">
        <el-select v-model="teacher.level" placeholder="请选择">
          <el-option label="高级教师" value="1"></el-option>
          <el-option label="首席教师" value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="学历">
        <el-input v-model="teacher.career"/> 
      </el-form-item>
      <el-form-item label="简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea"/> 
      </el-form-item>

      <!-- 上传头像 -->
      <el-form-item label="头像">
        <pan-thumb :image="teacher.avatar"/>
          <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像</el-button>
          <image-cropper
          v-show="imagecropperShow"
          :width="300"
          :height="300"
          :key="imagecropperKey"
          :url="BASE_API+'/eduoss/avatarUpload'"
          field="file"
          @close="close"
          @crop-upload-success="cropSuccess"/>
      </el-form-item>

      <el-form-item>
        <el-button saveBtnDisabled="saveBtnDisabled" type="" @click="onSubmit">创建</el-button>
        <el-button type="" @click="onCancel">取消</el-button>
     </el-form-item>
    </el-form>
  </div>
</template>

<script>
import teacherApi from '@/api/edu/teacher'
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'
export default {
  components: { ImageCropper, PanThumb},
  data() {
    return {
      saveBtnDisabled:false,
      teacher:{
        avatar: 'https://tsuiraku.oss-cn-chengdu.aliyuncs.com/edu/avatar/a3b941a8eb204a9fa8debfa71cc203b0file.png'
      },
      imagecropperShow:false,
      imagecropperKey:0,
      BASE_API: process.env.BASE_API, // 接口地址
    }
  },
  wacth: {
    $route(to, from) {
      this.init()
    }
  },
  created() {
    this.init()
  },
  methods: {
    onSubmit() {
      if(!this.teacher.id) {
        this.saveTeacher()
      } else {
        this.updateTeacher()
      }
    },
    saveTeacher() {
      teacherApi.addTeacher(this.teacher)
        .then(res => {
          this.$message({
            type: 'success',
            message: "添加成功"
          });
          // 重定向转到教师列表
          this.$router.push({
            path:'/teacher/table'
          })
        })
    },
    getTeacherInfo(id) {
      teacherApi.getTeacherInfoById(id)
       .then(res => {
         this.teacher = res.data.teacherInfo
        //  console.log("=========================================================")
        //  console.log(res)
        //  console.log(this.teacher)
       })
    },
    updateTeacher() {
      if(!this.teacher.avatar) {
        this.teacher.avatar = this.defalutAvatar
      }
      teacherApi.updateTeacherInfo(this.teacher)
        .then(res => {
          this.$message({
            type: 'success',
            message: '修改成功'
          });
          this.$router.push({path:'/teacher/table'})
        })
    },
    init() {
      if(this.$route.params && this.$route.params.id) {
        const id = this.$route.params.id
        this.getTeacherInfo(id)
        // console.log("=========================================================")
        // console.log(id)
      } else {
        this.teacher = {}
      }
    },
    close() {
      this.imagecropperShow = false
      this.imagecropperKey = this.imagecropperKey + 1
    },
    cropSuccess(data) {
      this.teacher.avatar = data.url
    },
    onCancel() {
      this.$router.push({path:'/teacher/table'})
    }
  }
}
</script>
