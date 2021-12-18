<template>
    <div class="app-container">
        <el-form :inline="true" class="demo-form-inline">
            <el-form-item>
                <el-select
                    v-model="searchObj.provinceCode"
                    placeholder="请选择省"
                        @change="provinceChanged">
                    <el-option
                        v-for="item in provinceList"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id"/>
                </el-select>
            </el-form-item>

            <el-form-item>
                <el-select
                v-model="searchObj.cityCode"
                placeholder="请选择市">
                    <el-option
                    v-for="item in cityList"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"/>
                </el-select>
            </el-form-item>

            <el-form-item>
                <el-input v-model="searchObj.hosname" placeholder="医院名称"/>
            </el-form-item>

            <el-button plain type="primary" icon="el-icon-search" @click="fetchData()">查询</el-button>
            <el-button plain type="default" @click="resetData()" icon="el-icon-delete" >清空</el-button>
        </el-form>

        <!-- banner列表 -->
        <el-table v-loading="listLoading" :data="list"
                border
            fit
            highlight-current-row>

            <el-table-column
            label="序号"
            width="60"
            align="center">
                <template slot-scope="scope">
                        {{ (page - 1) * limit + scope.$index + 1 }}
                </template>
            </el-table-column>

            <el-table-column label="医院logo">
                <template slot-scope="scope">
                <img :src="'data:image/jpeg;base64,'+scope.row.logoData" width="80">
                </template>
            </el-table-column>

            <el-table-column prop="hosname" label="医院名称"/>
            <el-table-column prop="param.hostypeString" label="等级" width="90"/>
            <el-table-column prop="param.fullAddress" label="详情地址"/>
            <el-table-column label="状态" width="80">
                <template slot-scope="scope">
                        {{ scope.row.status === 0 ? '未上线' : '已上线' }}
                </template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间"/>

            <el-table-column label="操作" width="230" align="center">
                <template slot-scope="scope">
                    <router-link :to="'/hospSet/hospital/show/' + scope.row.id">
                        <el-button plain type="primary" size="mini">查看</el-button>
                    </router-link>
                    <router-link :to="'/hospSet/hospital/schedule/' + scope.row.hoscode">
                        <el-button  plain type="warning" size="mini">排班</el-button>
                    </router-link>

                    <el-button  plain v-if="scope.row.status == 1"  type="primary" size="mini" @click="updateStatus(scope.row.id, 0)">下线</el-button>
                    <el-button  plain v-if="scope.row.status == 0"  type="danger" size="mini" @click="updateStatus(scope.row.id, 1)">上线</el-button>
                </template>

            </el-table-column>
        </el-table>

        <!-- 分页组件 -->
        <el-pagination
            :current-page="page"
            :total="total"
            :page-size="limit"
            style="padding: 30px 0; text-align: center;"
            layout="prev, pager, next"
            @current-change="fetchData"
        />
    </div>
</template>
<script>
import hospApi from '@/api/hosp'

export default {
       data() {
        return {
            listLoading: true, // 数据是否正在加载
            list: null, // 医院列表数据集合
            total: 0, // 数据库中的总记录数
            page: 1, // 默认页码
            limit: 10, // 每页记录数
            searchObj: {
                provinceCode:'',
                cityCode:''
            }, // 查询表单对象
            provinceList: [], // 所有省集合
            cityList: []   // 所有市集合
        }
    },
    created() {
        // 调用医院列表
        this.fetchData()
        // 调用查询所有省的方法
        this.findAllProvince()
    },
    methods: {
        // 更新医院上线状态
        updateStatus(id,status) {
            hospApi.updateStatus(id, status)
                .then(response => {
                    //刷新页面
                    this.fetchData(1)
                })
        },
        // 医院列表
        fetchData(page = 1) {
            this.page = page
            hospApi.getHospList(this.page, this.limit, this.searchObj)
                .then(response => {
                    this.list = response.data.content  // 每页数据集合
                    this.total = response.data.totalElements // 总记录数
                    this.listLoading = false // 加载图表不显示
                })
        },
        // 查询所有省
        findAllProvince() {
            hospApi.findByDictCode('Province')
                .then(response => {
                    this.provinceList = response.data
                })
        },
        // 点击某个省，显示里面市（联动）
        provinceChanged() {
            //初始化值
            this.cityList = []
            this.searchObj.cityCode = ''
            //调用方法，根据省id，查询下面子节点
            hospApi.findChildId(this.searchObj.provinceCode)
                .then(response => {
                    this.cityList = response.data
                })
        },
    }
}
</script>