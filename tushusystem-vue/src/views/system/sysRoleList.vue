<template>
  <el-main>
    <!-- 搜索栏 -->
    <el-form :model="listParm" ref="searchRef" :inline="true" size="small">
      <el-form-item>
        <el-input
          placeholder="请输入角色名称"
          v-model="listParm.roleName"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" @click="searchBtn">搜索</el-button>
        <el-button icon="el-icon-close" @click="resetBtn" style="color: #ff7670">重置</el-button>
        <el-button type="primary" @click="addBtn" icon="el-icon-plus"
        >新增</el-button
        >
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table :height="tableHeight" :data="tableData" border stripe>
      <el-table-column prop="roleName" label="角色名称"></el-table-column>
      <el-table-column prop="remark" label="备注"></el-table-column>
      <el-table-column label="操作" align="center" width="400">
        <template slot-scope="scope">
          <el-button
            type="success"
            size="small"
            icon="el-icon-edit"
            @click="assignBtn(scope.row)"
          >分配权限</el-button
          >
          <el-button type="primary" icon="el-icon-edit" size="small" @click="editBtn(scope.row)"
          >编辑</el-button
          >
          <el-button type="danger" icon="el-icon-delete" size="small" @click="deleteBtn(scope.row)"
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
      :page-sizes="[5, 10, 20,]"
      :page-size="listParm.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="listParm.total"
      background
    >
    </el-pagination>
    <!-- 新增、编辑 -->
    <sys-dialog
      :title="dialog.title"
      :visible="dialog.visible"
      :height="dialog.height"
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
        >
          <el-row>
            <el-col :span="12" :offset="0">
              <el-form-item prop="roleName" label="角色名称">
                <el-input v-model="addModel.roleName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="0">
              <el-form-item prop="roleType" label="角色类型">
                <el-select v-model="addModel.roleType" placeholder="请选择">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12" :offset="0">
              <el-form-item label="角色备注">
                <el-input v-model="addModel.remark"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </sys-dialog>
    <!-- 分配权限的弹框 -->
    <sys-dialog
      :title="assignDialog.title"
      :visible="assignDialog.visible"
      :width="assignDialog.width"
      :height="assignDialog.height"
      @onConfirm="assignConfirm"
      @onClose="assignClose"
    >
      <div slot="content">
        <el-tree
          ref="assignTree"
          :data="assignTreeDatas"
          node-key="menuId"
          :props="defaultProps"
          empty-text="暂无数据"
          :show-checkbox="true"
          default-expand-all
          :default-checked-keys="assignTreeCheckeds"
        ></el-tree>
      </div>
    </sys-dialog>
  </el-main>
</template>

<script>
import SysDialog from "@/components/system/SysDialog";
import {getRoleList, getAssingShow, addRole, editRole, deleteRole, assignSave} from "@/api/role";
export default {
  // 注册组件
  components: {
    SysDialog,
  },
  data() {
    return {
      defaultProps: {
        children: "children",
        label: "title",
      },
      assignDialog: {
        title: "",
        visible: false,
        width: 300,
        height: 450,
      },
      //角色类型
      options: [
        {
          value: "1",
          label: "系统管理员",
        },
        {
          value: "2",
          label: "普通用户",
        }
      ],
      //表单验证
      rules: {
        roleName: [
          {
            required: true,
            message: "请输入角色名称",
            trigger: "blur",
          },
        ],
        roleType: [
          {
            required: true,
            message: "请选择角色类型",
            trigger: "blur",
          },
        ],
      },
      //新增表单绑定的数据
      addModel: {
        type: "", //0：新增 1：编辑
        roleId: "",
        roleName: "",
        roleType: "",
        remark: "",
      },
      //弹框属性
      dialog: {
        height: 150,
        visible: false,
        title: "",
      },
      //表格高度
      tableHeight: 0,
      //搜索栏数据
      listParm: {
        roleName: "",
        currentPage: 1,
        pageSize: 5,
        total: 0,
      },
      //表格数据
      tableData: [],
      //角色Id
      roleId: "",
      //树数据
      assignTreeDatas: [],
      //角色原来的权限
      assignTreeCheckeds: [],

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
  async  assignConfirm(){
      let ids = this.$refs.assignTree
        .getCheckedKeys()
        .concat(this.$refs.assignTree.getHalfCheckedKeys());
      console.log(ids);
      let parm = {
        roleId: this.roleId,
        list: ids,
      };
      let res = await assignSave(parm);
     if (res && res.code == 200) {
      this.$message({ type: "success", message: res.msg });
      this.assignDialog.visible = false;
    }
    },
    assignTreeChecked(){

    },
    assignTreeData(){

    },
    assignClose() {
      this.assignDialog.visible = false;
    },
    async assignBtn(row) {
      console.log(row)
      //设置弹框属性
      this.assignDialog.title = "为【" + row.roleName + "】分配权限";
      this.assignDialog.visible = true;
     if (row.roleType == 1){
       let parm = {
             roleId: row.roleId,
       }
       this.roleId = row.roleId;
       let res = await getAssingShow(parm);
       if (res.code == 200){
         this.assignTreeDatas = res.data.menuList;
         this.assignTreeCheckeds = res.data.checkList;
       }
       //如果角色原来有权限
       if (this.assignTreeCheckeds.length > 0) {
         let newArr = [];
         this.assignTreeCheckeds.forEach((item) => {
           this.checked(item, this.assignTreeDatas, newArr);
         });
         this.assignTreeCheckeds = newArr;
       }
     }
      if (row.roleType == 2){
        let parm = {
          roleId: row.roleId,
        }
        this.roleId = row.roleId;
        let res = await getAssingShow(parm);
        console.log(res)
        if (res.code == 200){
          this.assignTreeDatas = res.data.menuList;
          this.assignTreeCheckeds = res.data.checkList;
        }
        //如果角色原来有权限
        if (this.assignTreeCheckeds.length > 0) {
          let newArr = [];
          this.assignTreeCheckeds.forEach((item) => {
            this.checked(item, this.assignTreeDatas, newArr);
          });
          this.assignTreeCheckeds = newArr;
        }
       }
    },
    //找出所有的回显数据
    checked(id, data, newArr) {
      data.forEach((item) => {
        if (item.menuId == id) {
          //是不是末级
          if (item.children && item.children.length == 0) {
            newArr.push(item.menuId);
          }
        } else {
          if (item.children && item.children.length != 0) {
            this.checked(id, item.children, newArr);
          }
        }
      });
    },
    //新增按
    addBtn() {
      //设置弹框属性
      this.dialog.title = "新增角色";
      this.dialog.visible = true;
      //清空表单
      this.$resetForm('addRef',this.addModel)
      //设置为新增
      this.addModel.type = '0'
    },
    //弹框确定
    onConfirm() {
      //表单验证
      this.$refs.addRef.validate(async(valid) => {
        if (valid) {
          let res = null;
          if (this.addModel.type == "0") {
            res = await addRole(this.addModel);
          } else {
            res = await editRole(this.addModel);
          }
          if (res && res.code == 200) {
            //信息提示
            this.$message({ type: "success", message: res.msg });
            //刷新列表
            this.getList();
          }
        }
      });
      this.dialog.visible = false;
    },
    //弹框关闭
    onClose() {
      this.dialog.visible = false;
    },
    //获取列表
    async getList() {
      let res = await getRoleList(this.listParm);
      if (res && res.code == 200) {
        //设置表格数据
        console.log(res);
        this.tableData = res.data.records;
        this.listParm.total = res.data.total;
      }
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
      //确定
      const confirm = await this.$myconfirm("确定删除该数据吗?");
      if (confirm) {
        let res = await deleteRole({ roleId: row.roleId });
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
      this.dialog.title = '编辑角色'
      this.dialog.visible = true;
      //清空表单
      this.$resetForm('addRef',this.addModel)
      //设置为编辑
      this.addModel.type = '1'
      //把当前要编辑的数据设置到表单数据域
      this.$objCoppy(row,this.addModel)
    },
    //重置按钮
    resetBtn() {
      this.listParm.roleName = "";
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
