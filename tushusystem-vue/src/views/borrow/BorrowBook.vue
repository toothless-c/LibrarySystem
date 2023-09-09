<template>
  <el-main>
    <div
      style="
        margin: 0px 0px 15px 0px;
        color: #67c23a;
        font-weight: 600;
        font-size: 15px;
      "
    >
      读者信息
    </div>
    <el-card class="box-card">
      <div slot="header" style="padding: 0px" class="clearfix">
        <el-form
          :model="searchParm"
          label-width="80px"
          :inline="true"
          size="mini"
        >
          <el-form-item label="姓名">
            <el-input v-model="searchParm.username"></el-input>
          </el-form-item>
          <el-form-item label="学号">
            <el-input v-model="searchParm.studentId"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search"  @click="getByUserName">查询</el-button>
            <el-button
              style="color: #ff7670"
              icon="el-icon-close"
              @click="resetBtn()"
            >重置</el-button
            >
          </el-form-item>
        </el-form>
      </div>
      <div>
        <el-form
          :model="showUser"
          ref="form"
          label-width="80px"
          :inline="true"
          size="small"
        >
          <el-form-item label="姓名">
            <el-input v-model="showUser.username"></el-input>
          </el-form-item>
          <el-form-item label="班级">
            <el-input v-model="showUser.className"></el-input>
          </el-form-item>
          <el-form-item label="学号">
            <el-input v-model="showUser.studentId"></el-input>
          </el-form-item>
          <el-form-item label="电话">
            <el-input v-model="showUser.phone"></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio v-model="showUser.sex" label="0">男</el-radio>
            <el-radio v-model="showUser.sex" label="1">女</el-radio>
          </el-form-item>
          <el-form-item label="状态">
            <el-radio v-model="showUser.checkStatus" label="0">未审核</el-radio>
            <el-radio v-model="showUser.checkStatus" label="1">已审核</el-radio>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
    <div
      style="
        margin: 15px 0px;
        color: #67c23a;
        font-weight: 600;
        font-size: 15px;
      "
    >
      图书列表
    </div>
    <div class="tushu">
      <el-card class="box-card">
        <el-form
          :model="listParm"
          label-width="80px"
          :inline="true"
          size="mini"
        >
          <el-form-item label="书名">
            <el-input v-model="listParm.name"></el-input>
          </el-form-item>
          <el-form-item label="图书编号">
            <el-input v-model="listParm.sm"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search"  @click="queryName">查询</el-button>
            <el-button
              style="color: #ff7670"
              icon="el-icon-close"
              @click="resetBtns()"
            >重置</el-button
            >
          </el-form-item>
          <el-form-item label="还书时间">
            <el-date-picker
              value-format="yyyy-MM-dd HH:mm:ss"
              format="yyyy-MM-dd HH:mm:ss"
              v-model="returnTime"
              type="date"
              placeholder="选择还书日期"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="borrow">借书</el-button>
          </el-form-item>
        </el-form>
      </el-card>
      <div class="tushuleft">
        <el-table
          ref="tables"
          :data="tableData"
          border
          stripe
          tooltip-effect="dark"
          style="width: 100%"
          @selection-change="handleSelectionChange">
          <el-table-column
            type="selection"
            width="55">
          </el-table-column>
          <el-table-column
            label="书名"
            prop="name">
          </el-table-column>
          <el-table-column
            prop="author"
            label="作者">
          </el-table-column>
          <el-table-column
            prop="bkClassify.classifyName"
            label="分类"
          >
          </el-table-column>
          <el-table-column
            prop="sm"
            label="编号">
          </el-table-column>
          <el-table-column
            prop="bookPlaceNum"
            label="书架号">
          </el-table-column>
          <el-table-column
            prop="price"
            label="价格">
          </el-table-column>
          <el-table-column
            prop="number"
            label="库存">
          </el-table-column>
        </el-table>
        <div class="block">
          <!-- 分页 -->
          <el-pagination
            @size-change="sizeChange"
            @current-change="currentChange"
            :current-page.sync="listParm.currentPage"
            :page-sizes="[6, 12, 30]"
            :page-size="listParm.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="listParm.total"
            background
          >
          </el-pagination>
        </div>
      </div>
    </div>
  </el-main>
</template>

<script>
import {borrow, QueryByUsernameOrStudentId, updateByid} from "@/api/borrow";
import {querylist, querylistss} from "@/api/book";
import eltTransfer from "elt-transfer/src/eltTransfer";
import { formateDate } from '../../../public/js/date';
export default {
  components: {
    "elt-transfer": eltTransfer,
  },
  data() {
    return {
      tableData: [],
      returnTime: "",
      number:[],
      id: [],
      //读者信息搜索框
      searchParm: {
        username: "",
      },
      showUser: {
        readerId: "",
        learnNum: "",
        username: "",
        idCard: "",
        sex: "",
        phone: "",
        className: "",
        checkStatus: "",
      },
      currentPage1: 5,
      currentPage2: 5,
      currentPage3: 5,
      currentPage4: 4,
      listParm: {
        name: "",
        sm: "",
        currentPage: 1,
        pageSize: 6,
      },
      borrowParm:{
        readerId:'',
        bookIds:[],
        returnTime:''
      }
    };
  },
  created() {
    this.getAll();
  },
  methods: {
    //借书
    borrow: async function () {
      // let ids = this.number.map(v => v.id)
      // let user_id = this.showUser.id
      // this.borrowParm.readerId = user_id;
      // this.borrowParm.bookIds.push(ids);
      // this.borrowParm.returnTime = this.returnTime;
      // console.log(this.borrowParm)
      // let res = await borrow(this.borrowParm);
      this.bookIds = [];
      console.log(this.tableData);
      if (!this.returnTime) {
        this.$message.error("请选择还书时间");
        return;
      }
      if (!this.showUser.id) {
        this.$message.error("请先查询读者是否可以借书");
        return;
      }
      if (this.tableData.length < 1) {
        this.$message.error("请选择要借的图书");
        return;
      }
      if (this.showUser.checkStatus == 0){
        this.$message.error("请先审核用户");
        return;
      }
      // //找到每个图书的id
      // for (let i = 0; i < this.tableData.length; i++) {
      //   this.bookIds.push(this.tableData[i].bookId);
      // }
      //提交

      let parm = {
        readerId: this.showUser.id,
        bookIds: this.number.map(v => v.id),
        returnTime: this.returnTime,
      };
      let res = await borrow(parm);
      console.log(res)
      if (res && res.code == 200) {
        this.$message.success(res.msg);
        setTimeout(function () {
          window.location.reload();
        }, 3000);
      }
    },
    getAll: async function () {
      let res =  await querylistss(this.listParm);
      console.log(res.data)
      this.listParm.total = res.data.total;
      this.listParm.pageSize = res.data.size;
      this.tableData = res.data.records
    },
    //页数改变时触发
    currentChange(val) {
      this.listParm.currentPage = val;
      this.getList();
    },
    //页容量改变时触发
    sizeChange(val) {
      this.listParm.pageSize = val;
      this.getList();
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    },
    toggleSelection(rows) {
      console.log(rows)
      // if (rows) {
      //   rows.forEach(row => {
      //     this.$refs.multipleTable.toggleRowSelection(row);
      //   });
      // } else {
      //   this.$refs.multipleTable.clearSelection();
      // }
    },
    handleSelectionChange(val) {
      this.number = val
    },
    //查询学生信息
    async getByUserName(){
      let res = await  QueryByUsernameOrStudentId(this.searchParm)
      if (res && res.code == 200 && res.data) {
        this.showUser = res.data[0];
      }
    },
    //查询图书信息
    queryName(){
      this.getAll()
    },
    //重置按钮
    resetBtn() {
      this.returnTime = "";
      this.searchParm.username = "";
      this.searchParm.studentId="";
      this.showUser.studentId = "";
      this.showUser.username = "";
      this.showUser.sex = "";
      this.showUser.phone = "";
      this.showUser.className = "";
      this.showUser.checkStatus = "";
      this.getAll()
    },
    resetBtns(){
      this.listParm.name = "";
      this.listParm.sm = ""
      this.getAll()
    }
  },
};
</script>

<style scoped>
::v-deep .el-card__header {
  padding: 15px 0px 0px 0px;
}
::v-deep .el-card__body {
  padding: 15px 0px 0px 0px;
}
.tushuleft{

}
</style>
