<template>
  <!-- element的标签，只需要当做一个普通的div看待就可以 -->
  <el-main>
    <!-- 搜索栏
     model :绑定表单的数据，通常是一个对象
     ref ： 相当于div的id
     rules ：表单验证的规则
     label-width ： 表单域标签的宽度
     inline ： 是否在同一行显示
     size ：尺寸
     <c:foreach>
     <c:choose>
     <c:when>
      -->
      <div style="margin-bottom: 8px">
        <el-button type="primary" @click="addBtn" icon="el-icon-plus"
        >新增</el-button
        >
      </div>
    <!-- 表格
     data： 表格的数据
      -->
    <el-table :height="tableHeight" :data="tableData" border stripe>
      <el-table-column prop="classifyName" label="分类名称"></el-table-column>
      <el-table-column label="操作" align="center" width="180">
        <template slot-scope="scope">
          <el-button
            type="primary"
            icon="el-icon-edit"
            size="small"
            @click="editBtn(scope.row)"
          >编辑</el-button
          >
          <el-button
            type="danger"
            icon="el-icon-edit"
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
      :page-sizes="[5, 10]"
      :page-size="listParm.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="listParm.total"
      background
    >
    </el-pagination>
    <!-- 新增或编辑弹框 -->
    <sys-dialog
      :title="dialog.title"
      :visible="dialog.visible"
      :width="dialog.width"
      :height="dialog.height"
      @onClose="onClose"
      @onConfirm="onConfirm"
    >
      <div slot="content">
        <!-- el-form : 当做一个普通的form标签
        model ：表单绑定的数据对象
        ref ： 相当于div的id , 唯一的
        rules ： 表单验证规则
        label-width ： 表单域标签的宽度
        inline ： 是否在同一行展示
        size ：尺寸
         -->
        <el-form
          :model="addModel"
          ref="addRef"
          :rules="rules"
          label-width="80px"
          :inline="false"
          size="small"
          style="margin-right: 40px"
        >
          <!-- el-row  : 代表一行，分为24等分
              el-col ： 代表列
         -->
          <el-row>
            <el-col :span="12" :offset="0">
              <el-form-item prop="name" label="分类名称">
                <el-input v-model="addModel.classifyName"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </sys-dialog>
  </el-main>
</template>

<script>
import {addClassify, deleteClassify, editClassify, getClassifyList} from "@/api/classify";
//引入弹框组件
import SysDialog from "@/components/system/SysDialog";
export default {
  //注册组件
  components: {
    SysDialog,
  },
  data() {
    return {
      //表单验证规则
      rules: {
        name: [
          {
            trigger: "blur",
            required: true,
            message: "请填写分类名",
          },
        ]
      },
      //表单绑定的数据
      addModel: {
        type: "", // 0:新增 1：编辑
        classifyId: "",
        classifyName: "",
      },
      //弹框属性
      dialog: {
        title: "",
        visible: false,
        width: 630,
        height: 200,
      },
      //表格的高度
      tableHeight: 0,
      //列表查询的参数
      listParm: {
        currentPage: 1,
        pageSize: 6,
        total: 0,
      },
      //表格的数据
      tableData: [],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    //获取列表
    async getList() {
      let res = await getClassifyList(this.listParm);
      console.log(res);
      if (res && res.code == 200) {
        this.tableData = res.data.records;
        this.listParm.total = res.data.total;
        this.listParm.pageSize = res.data.size;
      }
    },
    //弹框确定
    onConfirm() {
      //表单验证
      this.$refs.addRef.validate(async (valid) => {
        if (valid) {
          let res = null;
          if (this.addModel.type == "0") {
            res = await addClassify(this.addModel);
          } else {
            res = await editClassify(this.addModel);
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
    currentChange(val) {
      this.listParm.currentPage = val;
      this.getList();
    },
    //页容量改变时触发
    sizeChange(val) {
      this.listParm.pageSize = val;
      this.getList();
    },
    //删除按钮
    async deleteBtn(row) {
      let confirm = await this.$myconfirm("确定删除该数据吗?");
      if (confirm) {
        console.log(row.classifyId)
        let res = await deleteClassify({ id: row.classifyId });
        console.log(res)
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
      //清空表单
      this.$resetForm("addRef", this.addModel);

      //把要编辑的数据复制到表单绑定的数据域
      this.$objCoppy(row, this.addModel);
      //设置编辑
      this.addModel.type = "1";
    },
    //新增按钮
    addBtn() {
      //设置弹框属性
      this.dialog.title = "新增用户";
      this.dialog.visible = true;
      //清空表单
      this.$resetForm("addRef", this.addModel);
      //设置为新增
      this.addModel.type = "0";
    },
  },
  mounted() {
    this.$nextTick(() => {
      //计算表格高度
      this.tableHeight = window.innerHeight - 220;
    });
  },
};
</script>

<style lang="scss" scoped>
</style>
