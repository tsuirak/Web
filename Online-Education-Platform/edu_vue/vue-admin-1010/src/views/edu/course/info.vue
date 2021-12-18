<template>
  <div class="app-container">
    <h2 style="text-align: center">发布新课程</h2>
    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息" /> 
      <el-step title="创建课程大纲" />
      <el-step title="最终发布" />
    </el-steps>

       <el-form label-width="120px">

        <el-form-item label="标题">
            <el-input v-model="courseInfo.title" placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"/>
        </el-form-item>

        <!-- 所属分类 TODO -->
        <el-form-item label="课程分类">
            <el-select
                v-model="courseInfo.subjectParentId"
                placeholder="请选择一级分类" @change="getSubjectTwoList">

                <el-option
                    v-for="subject in subjectOneList"
                    :key="subject.id"
                    :label="subject.title"
                    :value="subject.id"/>

            </el-select>


            <el-select v-model="courseInfo.subjectId" placeholder="请选择二级分类">
                <el-option
                    v-for="subject in subjectTwoList"
                    :key="subject.id"
                    :label="subject.title"
                    :value="subject.id"/>
            </el-select>
        </el-form-item> 


        <!-- 课程讲师 TODO -->
        <!-- 课程讲师 -->
        <el-form-item label="任课教师">
            <el-select
                v-model="courseInfo.teacherId"
                placeholder="请选择">
                <el-option
                    v-for="teacher in teacherList"
                    :key="teacher.id"
                    :label="teacher.name"
                    :value="teacher.id"/>
            </el-select>
        </el-form-item>

        <el-form-item label="总课时">
            <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数"/>
        </el-form-item>

        <!-- 课程简介 TODO -->
        <el-form-item label="简介">
            <el-input v-model="courseInfo.description" placeholder="请输入课程的简介"></el-input>
        </el-form-item>

        <!-- 课程封面 TODO -->
        <!-- 课程封面-->
        <el-form-item label="课程封面">

            <el-upload
                :show-file-list="false"
                :on-success="handleCoverSuccess"
                :before-upload="beforeCoverUpload"
                :action="BASE_API+'/eduoss/avatarUpload'"
                class="avatar-uploader">
                <img :src="courseInfo.cover" width="500px">
            </el-upload>

        </el-form-item>

        <el-form-item label="价格">
            <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元"/> 元
        </el-form-item>

        <el-form-item>
            <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存并下一步</el-button>
        </el-form-item>
    </el-form>
  </div>
</template>

<script>
import course from '@/api/edu/course'
import subject from '@/api/edu/subject'
import Tinymce from '@/components/Tinymce'
export default {
    components: { Tinymce },
    data() {
        return {
            saveBtnDisabled: false,
            courseInfo:{
                subjectId: '',
                cover: 'https://tsuiraku.oss-cn-chengdu.aliyuncs.com/edu/image/cover.png'
            },
            teacherList:[],
            subjectOneList:[],
            subjectTwoList:[],
            BASE_API: process.env.BASE_API,
            courseId: ''
        }

    },
    created() {
        if(this.$route.params && this.$route.params.id) {
            this.courseId = this.$route.params.id
            this.getCourseInfoById()
        } else {
            this.getOneSubject()
            this.getTeacherList()    
        }
    },
    methods: {
        getCourseInfoById() {
            course.getCourseInfoById(this.courseId)
                .then(res => {
                    this.courseInfo = res.data.courseInfo
                    subject.getSubjectList()
                        .then(res => {
                            this.subjectOneList = res.data.items
                            for(var i = 0; i < this.subjectOneList.length; i++) {
                                var oneSubject = this.subjectOneList[i]
                                if(oneSubject.id == this.courseInfo.subjectParentId) {
                                    this.subjectTwoList = oneSubject.children
                                }
                            }
                        })
                        this.getTeacherList()
                })
        },
        getSubjectTwoList(value) {
            for(var i = 0; i < this.subjectOneList.length; i++) {
                var oneSubject = this.subjectOneList[i];
                if(oneSubject.id === value) {
                    this.subjectTwoList = oneSubject.children
                    this.courseInfo.subjectId = ''
                }
            }
        },
        getOneSubject() {
        subject.getSubjectList()
        .then(res => {
            this.subjectOneList = res.data.items
        })
    },
        getTeacherList() {
            course.getTeacherInfo()
                .then(res => {
                    this.teacherList = res.data.list
                })
        },
        addCourse() {
             course.addCourseInfo(this.courseInfo)
                .then(res => {
                    this.$message({
                        type: 'success',
                        message: '添加课程信息成功'
                    })
                    this.$router.push({path:'/course/chapter/' + res.data.courseId})
                })
        },
        updateCourse() {
            course.updateChapterInfo(this.courseInfo)
                .then(res => {
                    this.$message({
                        type: 'success',
                        message: '修改课程信息成功'
                    })
                    this.$router.push({path:'/course/chapter/' + this.courseId})
                })
        },
        saveOrUpdate() {
            if(!this.courseInfo.id) {
                this.addCourse()
            } else {
                this.updateCourse()
            }
        },
        handleCoverSuccess(res, file) {
            console.log("=====================================")
            this.courseInfo.cover = res.data.url
            console.log(this.courseInfo.cover)
        },
        beforeCoverUpload(file) {
            const isJPG = file.type === 'image/jpeg'
            const isLt2M = file.size / 1024 / 1024 < 2
            if(!isJPG) {
                this.$message.error('上传头像只能是 JPG 格式')
            }
            if(!isLt2M) {
                this.$message.error('上传图片大小不能超过 2MB')
            }
            return isJPG && isLt2M
        }
    }
}
</script>

<style scoped>
.tinymce-container {
  line-height: 29px;
}
</style>