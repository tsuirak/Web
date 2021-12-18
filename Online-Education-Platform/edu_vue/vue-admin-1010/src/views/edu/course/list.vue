<template>
  <div class="app-container">
   <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="courseQuery.name" placeholder="姓名"/>
      </el-form-item>

      <el-form-item>
        <el-select v-model="courseQuery.level" clearable placeholder="状态">
          <el-option :value="Normal" label="已发布"/>
          <el-option :value="Draft" label="未发布"/>
        </el-select>
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

  <!-- 中间表单 -->
 <el-table
    :data="list.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
    style="width: 100%" :row-style="{height: '100px'}"
    :cell-style="{ padding: '0px'}">
    <el-table-column
      label="课程"
      prop="title">
    </el-table-column>
     <el-table-column
      label="入职日期"
      prop="gmtCreate">
      <template slot-scope="scope">
          {{ scope.row.status === 'Normal' ? '已发布' : '未发布' }}
      </template>
    </el-table-column>
    <el-table-column
      label="课时"
      prop="lessonNum">
    </el-table-column>
    <el-table-column
      label="创建时间"
      prop="gmtCreate">
    </el-table-column>
    <el-table-column
      label="浏览数量"
      prop="viewCount">
    </el-table-column>

    <!-- 编辑课程 to-do -->
    <!-- <el-table-column   
      align="right">
      <template slot-scope="scope">
        <router-link :to="'/teacher/edit/'+scope.row.id">
        <el-button
          size="mini"
          icon="el-icon-edit">编辑课程基本信息
          </el-button>
        </router-link>
    <router-link :to="'/teacher/edit/'+scope.row.id">
          <el-button
          size="mini"
          icon="el-icon-edit">编辑课程内容
          </el-button>
        </router-link>
        <el-button
          size="mini"
          type="danger"
          @click="removeDataById(scope.row.id)">删除
          </el-button>
      </template>
    </el-table-column> -->

  </el-table>

  <!-- 分页 -->
  <!-- <el-pagination
      :current-page="page"
      :page-size="limit" 
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
      >
    </el-pagination> -->
  </div>
</template>

<script>
import course from '@/api/edu/course'
export default {
    data() {
        return {
            page:1, // 当前页
            limit:10, // 每页记录数
            list: null, // 查询后返回的集合
            courseQuery: {},
            courseId: ''
        }
    },
    created() {
        this.getList()
    },
    methods: {
        getList() {
            course.getCourseList()
                .then(res => {
                    this.list = res.data.list
                })
        },
        resetData() {
            this.courseQuery = {}
            this.getList()
        },
        removeDataById(id) {
        this.$confirm('此操作将永久删除该信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          course.deleteCourse(id)
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