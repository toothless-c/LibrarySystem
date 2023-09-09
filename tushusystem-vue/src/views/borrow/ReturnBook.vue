<template>
  <el-main>
    <!-- 搜索栏 -->
    <el-form :model="listParm" label-width="80px" :inline="true" size="small">
      <el-form-item label="学号">
        <el-input v-model="listParm.username"></el-input>
      </el-form-item>
      <el-form-item label="借书状态">
        <el-select v-model="listParm.borrowStatus" placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" @click="searchBtn">搜索</el-button>
        <el-button icon="el-icon-close" @click="resetBtn">重置</el-button>
<!--        <el-button type="primary" icon="el-icon-edit" @click="returnBtn"-->
<!--        >批量还书</el-button-->
<!--        >-->
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table
      ref="tables"
      :height="tableHeight"
      :data="tableData"
      border
      stripe
    >
<!--      <el-table-column type="selection" width="55"> </el-table-column>-->
      <el-table-column prop="bkBooks[0].name" label="图书名称"> </el-table-column>
      <el-table-column prop="bkBooks[0].bookPlaceNum" label="书架号"> </el-table-column>
      <el-table-column prop="bkUser[0].studentId" label="学号"> </el-table-column>
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
          <el-tag v-if="scope.row.borrowStatus=='1'">在借中</el-tag>
          <el-tag type="success" v-if="scope.row.borrowStatus=='2'">已还</el-tag>
          <el-tag type="danger" v-if="scope.row.borrowStatus=='3'">拒绝</el-tag>
<!--          <el-tag v-if="scope.row.returnStatus == '1'">在借中</el-tag>-->
<!--          <el-tag type="success" v-if="scope.row.returnStatus == '2'"-->
<!--          >已还</el-tag-->
<!--          >-->
<!--          <el-tag type="danger" v-if="scope.row.returnStatus == '3'"-->
<!--          >拒绝</el-tag-->
<!--          >-->
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="210">
        <template slot-scope="scope">
          <el-button
            type="primary"
            icon="el-icon-edit"
            size="small"
            v-if="scope.row.borrowStatus=='1'"
            @click="alongReturnBtn(scope.row)"
          >还书</el-button
          >
          <el-button
            v-if="scope.row.borrowStatus=='3'"
            type="danger"
            icon="el-icon-edit"
            size="small"
            @click="ExceptioReturnBtn(scope.row)"
          >异常还书</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
      @size-change="sizeChange"
      @current-change="currentChange"
      :current-page.sync="listParm.currentPage"
      :page-sizes="[10, 20, 40, 80, 100]"
      :page-size="listParm.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="listParm.total"
      background
    >
    </el-pagination>
    <!-- 异常还书备注 -->
    <sys-dialog
      :title="dialog.title"
      :width="dialog.width"
      :height="dialog.height"
      :visible="dialog.visible"
      @onClose="onClose"
      @onConfirm="onConfirm"
    >
      <div slot="content">
        <el-form
          :model="exception"
          ref="exceptionRef"
          :rules="rules"
          label-width="80px"
          size="small"
        >
          <el-form-item prop="type" label="异常类型">
            <el-select v-model="exception.type" placeholder="请选择">
              <el-option
                v-for="item in exoptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="excepionText" label="备注">
            <el-input
              type="textarea"
              v-model="exception.excepionText"
            ></el-input>
          </el-form-item>
        </el-form>
      </div>
    </sys-dialog>
  </el-main>
</template>

<script>
import SysDialog from "@/components/system/SysDialog";
import {
  returnBorrowApi,
  returnBooks,
  exceptionBooksApi
} from "@/api/borrow";
import axios from "axios";
export default {
  components: {
    SysDialog,
  },
  data() {
    return {
      rules: {
        type: [
          { required: true, message: "请选择异常类型", trigger: "change" },
        ],
        excepionText: [
          { required: true, message: "请填写备注", trigger: "change" },
        ],
      },
      exoptions: [
        {
          value: "0",
          label: "异常还书",
        },
        {
          value: "1",
          label: "丢失",
        },
      ],
      exception: {
        borrowId: "",
        bookId: "",
        type: "",//return_start
        excepionText: "",
      },
      //弹框属性定义
      dialog: {
        width: 630,
        height: 150,
        title: "还书备注",
        visible: false,
      },
      bookIds: [],
      tableHeight: 0,
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
      tableData: [],
      listParm: {
        currentPage: 1,
        pageSize: 10,
        username: "",
        borrowStatus: "1",
        total: 0,
      },
    };
  },
  mounted() {
    this.$nextTick(() => {
      this.tableHeight = window.innerHeight - 220;
    });
  },
  created() {
    this.returnBorrow();
  },
  methods: {
    onConfirm() {
      this.$refs.exceptionRef.validate(async (valid) => {
        if (valid) {
          console.log(this.exception)
          let res = await exceptionBooksApi(this.exception);
          console.log(res)
          if (res && res.code == 200) {
            this.$message.success(res.msg)
            this.returnBorrow();
            this.dialog.visible = false;
          }
        }
      });
    },
    onClose() {
      this.dialog.visible = false;
    },
    //异常还书的事件
    ExceptioReturnBtn(row) {
      this.exception.borrowId = row.id
      this.exception.bookId = row.bkUser[0].id
      // console.log(row.borrowId)
      // console.log(row.bookId)
      console.log(row.id)
      console.log(row.bkUser[0].id)
      this.dialog.visible = true;
    },
    //单独还书的事件
    async alongReturnBtn(row) {
      this.bookIds = [];
      console.log(row);
      let confirm = await this.$myconfirm("确定还书吗?");
      if (confirm) {
        if (row.borrowStatus == "1") {
          //在借中的才能还
          // var obj = [];
          // obj.borrowId = Number(row.id);
          // obj.bookId =Number(row.bkUser[0].id);//图书id
          // this.bookIds.push(obj)
          // console.log(this.bookIds)
          // let res = await returnBooks(this.bookIds);
          // console.log(res)
          // if (res && res.code == 200) {
          //   this.$message.success(res.msg);
          //   this.returnBorrow();
          // }
          var data  = [{"borrowId": row.id, "bookId": row.bkUser[0].id}]
          axios.post(`http://106.12.127.251:9090/api/borrow/returnBook`,data).then(res=>{
            console.log(res.data)
            if(res && res.data.code ==200){
              this.$message.success(res.data.msg);
              this.returnBorrow();
            }
          })
        }
      }
    },
    currentChange(val) {
      this.listParm.currentPage = val;
      this.returnBorrow();
    },
    sizeChange(val) {
      this.listParm.pageSize = val;
      this.returnBorrow();
    },
    async returnBtn() {
      this.bookIds = [];
      let confirm = await this.$myconfirm("确定还书吗?");
      if (confirm) {
        let datas = this.$refs.tables.selection;
        if (datas.length == 0) {
          this.$message.error("请选择要还的书！");
          return;
        }
        for (let i = 0; i < datas.length; i++) {
          let obj = {};
          obj.borrowId = datas[i].borrowId;
          obj.bookId = datas[i].bookId;
          this.bookIds.push(obj);
        }
        let res = await returnBooks(this.bookIds);
        if (res && res.code == 200) {
          this.$message.success(res.msg);
          this.returnBorrow();
        }
      }
    },
    resetBtn() {
      this.listParm.currentPage = 1;
      this.listParm.username = "";
      this.listParm.borrowStatus = "";
      this.returnBorrow();
    },
    searchBtn() {
      this.returnBorrow();
    },
    async returnBorrow() {
      let res = await returnBorrowApi(this.listParm);
      if (res && res.code == 200) {
        this.tableData = res.data.records
        this.listParm.total = res.data.total;
      }
    },
  },
};
</script>

<style>
</style>
