<template>
  <el-main>
    <!-- 搜索栏 -->
    <el-form :model="lookParm" label-width="80px" :inline="true" size="mini">
      <el-form-item label="图书名称">
        <el-input v-model="lookParm.bookName"></el-input>
      </el-form-item>


      <el-form-item>
        <el-button icon="el-icon-search" @click="searchBtn">搜索</el-button>
        <el-button style="color: #ff7670" icon="el-icon-close" @click="reset">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table
      size="small"
      ref="tables"
      :height="tableHeight"
      :data="tableData"
      border
      stripe
    >
      <el-table-column prop="bkBooks[0].name" label="图书名称"> </el-table-column>
      <el-table-column prop="bkBooks[0].bookPlaceNum" label="书架号"> </el-table-column>
      <el-table-column prop="bkUser[0].username" label="读者"> </el-table-column>
      <el-table-column prop="bkUser[0].className" label="班级"> </el-table-column>
      <el-table-column prop="bkUser[0].phone" label="电话"> </el-table-column>
      <el-table-column prop="borrowTime" label="借书时间"> </el-table-column>
      <el-table-column prop="returnTime" label="还书时间"> </el-table-column>
      <el-table-column
        prop="borrowStatus"
        label="借书状态"
        align="center"
        width="100"
      >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.borrowStatus == '1'">在借中</el-tag>
          <el-tag type="success" v-if="scope.row.borrowStatus == '2'"
          >已还</el-tag
          >
          <el-tag type="danger" v-if="scope.row.borrowStatus == '3'"
          >拒绝</el-tag
          >
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
      @size-change="sizeChange"
      @current-change="currentChange"
      :current-page.sync="lookParm.currentPage"
      :page-sizes="[10, 20, 40, 80, 100]"
      :page-size="lookParm.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="lookParm.total"
      background
    >
    </el-pagination>
  </el-main>
</template>

<script>
import { getLookBorrowList } from "@/api/borrow";
import {getToken}from "@/utils/auth"
import Cookies from "js-cookie";
export default {
  data() {
    return {
      tableData: [],
      applytions: [
        {
          value: "0",
          label: "待审核",
        },
        {
          value: "1",
          label: "已审核",
        },
        {
          value: "2",
          label: "拒绝",
        },
      ],
      timetions: [
        {
          value: "1",
          label: "到期",
        },
        {
          value: "0",
          label: "未到期",
        },
      ],
      exceoptions: [
        {
          value: "1",
          label: "正常还书",
        },
        {
          value: "2",
          label: "异常还书",
        },
      ],
      options: [
        {
          value: "1",
          label: "在借中",
        },
        {
          value: "2",
          label: "已还",
        },
        {
          value: "3",
          label: "拒绝",
        },
      ],
      lookParm: {
        total: 0,
        currentPage: 1,
        pageSize: 10,
        bookName: "",
        id: 0,
      },
      tableHeight:0
    };
  },
  mounted() {
    this.$nextTick(() =>{
      this.tableHeight = window.innerHeight -250
    })
    this.getLookBorrowList();
  },
  methods: {
    reset(){
      this.lookParm.bookName = ""
      this.lookParm.applyStatus = ""
      this.getLookBorrowList()
    },
    searchBtn(){
      this.getLookBorrowList()
    },
    currentChange(){

    },
    sizeChange(){

    },
    async getLookBorrowList() {
      let id =  Cookies.get("userId")
      let userType = Cookies.get("userType")
      this.lookParm.id = id;
      if (userType == 1){
        let  lookParm = {
            total: 0,
            currentPage: 1,
            pageSize: 10,
            bookName: "",
            id: "",
          };
        let res = await getLookBorrowList(lookParm);
        console.log(res)
        if (res && res.code == 200) {
          this.tableData = res.data.records;
          this.lookParm.total = res.data.total;
        }
      }
      if (userType == 0){
        let res = await getLookBorrowList(this.lookParm);
        console.log(res)
        if (res && res.code == 200) {
          this.tableData = res.data.records;
          this.lookParm.total = res.data.total;
        }
      }

    },
  },
};
</script>

<style lang="scss" scoped>

</style>
