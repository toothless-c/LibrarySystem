<template>
  <el-main>
    <!-- 搜索栏 -->
    <el-form :model="listParm" :inline="true" size="small">
      <el-form-item>
        <el-input
          placeholder="请输入学号"
          v-model="listParm.studentId"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-input
          placeholder="请输入姓名"
          v-model="listParm.username"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-input
          placeholder="请输入电话号码"
          v-model="listParm.phone"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" @click="searchBtn">搜索</el-button>
        <el-button style="color: #ff7670" icon="el-icon-close" @click="resetBtn"
        >重置</el-button
        >
        <el-button type="primary" icon="el-icon-plus" @click="addBtn"
        >新增</el-button
        >
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table :height="tableHeight" :data="tableData" border stripe>
      <el-table-column prop="username" label="账号"></el-table-column>
      <el-table-column prop="studentId" label="学号"></el-table-column>
      <el-table-column prop="className" label="班级"></el-table-column>
      <el-table-column prop="phone" label="电话"></el-table-column>
      <el-table-column prop="sex" label="性别" width="80px">
        <template slot-scope="scope" >
          <el-tag v-if="scope.row.sex == '0'">男</el-tag>
          <el-tag v-if="scope.row.sex == '1'">女</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="checkStatus" label="审核状态" width="80px">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.checkStatus"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="1"
              inactive-value="0"
              @change="changeSwitch(scope.row)"
            >
            </el-switch>
          </template>
      </el-table-column>
      <el-table-column width="130px">
        <template slot-scope="scope">
          <el-button type="warning" @click="reset(scope.row)" style="margin-bottom:15px;">密码重置</el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            icon="el-icon-edit"
            type="primary"
            size="small"
            @click="editBtn(scope.row)"
          >编辑</el-button
          >
          <el-button
            icon="el-icon-delete"
            type="danger"
            size="small"
            @click="deleteBtn(scope.row)"
          >删除</el-button
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
    <!-- 新增弹框 -->
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
          :model="addModel"
          ref="addRef"
          :rules="rules"
          label-width="80px"
          size="small"
          style="margin-right: 30px"
        >
          <el-row>
            <el-col :span="12" :offset="0">
              <el-form-item prop="username" label="账号">
                <el-input v-model="addModel.username"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="0">
              <el-form-item prop="phone" label="电话">
                <el-input v-model="addModel.phone"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12" :offset="0">
              <el-form-item prop="studentId" label="学号">
                <el-input v-model="addModel.studentId"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="0">
              <el-form-item prop="className" label="班级">
                <el-input v-model="addModel.className"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
<!--            <el-col :span="12" :offset="0">-->
<!--              <el-form-item prop="password" label="密码">-->
<!--                <el-input v-model="addModel.password" v-if="addModel.password=null"></el-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
          </el-row>
          <el-row>
            <el-col :span="12" :offset="0">
              <el-form-item label="性别">
                <el-radio-group v-model="addModel.sex">
                  <el-radio :label="'0'">男</el-radio>
                  <el-radio :label="'1'">女</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </sys-dialog>
  </el-main>
</template>

<script>
import SysDialog from "@/components/system/SysDialog";
import { getAll , addBkUser ,editpassword ,editcheckstatus, editBkUser ,deletebkuser} from "@/api/bkuser";
export default {
  //注册组件
  components: {
    SysDialog,
  },
  data() {
    return {
      value2: true,
      //弹框属性
      dialog: {
        title: "",
        width: 640,
        height: 230,
        visible: false,
      },
      rules: {
        studentId: [{ required: true, message: "请填写学号", trigger: "blur" }],
        username: [{ required: true, message: "请填写姓名", trigger: "blur" }],
        idCard: [
          { required: true, message: "请填写身份证号", trigger: "blur" },
        ],
        phone: [{ required: true, message: "请填写电话", trigger: "blur" }],
        password: [{ required: true, message: "请填写密码", trigger: "blur" }],
        className: [{ required: true, message: "请填写班级", trigger: "blur" }],
      },
      //表单属性
      addModel: {
        type: "",
        id: "",
        studentId: "",
        username: "",
        sex: "",
        phone: "",
        password: "",
        className: "",
      },
      //表格高度
      tableHeight: 0,
      //表格数据
      tableData: [],
      //列表参数
      listParm: {
        total: 0,
        currentPage: 1,
        pageSize: 10,
        username: "",
        phone: "",
        studentId: "",
      },
    };
  },
  mounted() {
    this.$nextTick(() => {
      this.tableHeight = window.innerHeight - 220;
    });
  },
  created() {
    this.getList();
  },
  methods: {
    async changeSwitch(row){
    if (row.checkStatus == 1){
     let res = await editcheckstatus(row.id,row.checkStatus)
      if (res && res.code == 200) {
        //信息提示
        this.$message({type: "success", message: "审核通过"});
      }
     }else if(row.checkStatus == 0){
      let res = await editcheckstatus(row.id,row.checkStatus)
      if (res && res.code == 200) {
        //信息提示
        this.$message({type: "success", message: "关闭成功"});
      }
    }
    },
    async reset(row) {
      let confirm = await this.$myconfirm("是否重置密码?");
      if (confirm){
        let res = await editpassword(row.id);
        if (res && res.code == 200) {
          //信息提示
          this.$message({type: "success", message: res.msg});
        }
      }
    },
    //弹框确定
    onConfirm() {
      this.$refs.addRef.validate(async (valid) => {
        if (valid) {
          let res = null;
          if (this.addModel.type == "0") {
            res = await addBkUser(this.addModel);
          }else {
            res = await editBkUser(this.addModel)
          }
          if (res && res.code == 200) {
            //信息提示
            this.$message({ type: "success", message: res.msg });
            //刷新表格
            this.getList();
            //关闭弹框
            this.dialog.visible = false;
          }
        }
      });
    },
    //弹框关闭
    onClose() {
      this.dialog.visible = false;
    },
    //页数改变时触发
    currentChange(val) {},
    //页容量改变时触发
    sizeChange(val) {},
    //获取列表
    async getList() {
      let res = await getAll(this.listParm);
      if (res && res.code == 200) {
        console.log(res.data.records)
        this.tableData = res.data.records;
        this.listParm.total = res.data.total;
      }
    },
    //删除按钮
   async deleteBtn(row) {
      let confirm = await this.$myconfirm("确定删除该数据吗?");
      if (confirm){
        let res = await deletebkuser({id:row.id})
        if (res && res.code == 200) {
          //信息提示
          this.$message({ type: "success", message: res.msg });
          //刷新表格
          this.getList();
        }
      }
    },
    //编辑按钮
    editBtn(row) {
      //设置弹框属性
      this.dialog.title = "编辑用户";
      this.dialog.visible = true;
      this.$objCoppy(row, this.addModel);
    },
    //新增按钮
    addBtn() {
      this.dialog.title = "新增读者";
      this.dialog.visible = true;
      this.$resetForm("addRef", this.addModel);
      this.addModel.type = "0"
    },
    //重置按钮
    resetBtn() {
      this.listParm.studentId = "";
      this.listParm.username ="";
      this.listParm.phone="";
      this.getList();
    },
    //搜索按钮
    searchBtn() {
      this.getList();
    },
  },
};
</script>

<style lang="scss" scoped>
</style>
