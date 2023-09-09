<!--弹出框组件
  参数说明：
  title:弹出框标题
  visible:控制弹出框的显示和影藏
  width:弹出框的宽度，百分百，0~100的整数
  before-close：弹出框右上角关闭事件
  close-on-click-modal:防止点击弹出框以外的区域造成弹出框关闭
-->
<template>
  <div>
    <el-dialog
      top="5vh"
      :title="title"
      :visible.sync="visible"
      :width="width + 'px'"
      :before-close="onClose"
      :close-on-click-modal='false'
    >
      <div class="containner" :style="{ height: height + 'px' }">
        <slot name="content"></slot>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="onClose">取 消</el-button>
        <el-button type="primary" @click="onConfirm">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      default: "标题",
    },
    visible: {
      type: Boolean,
      default: false,
    },
    width: {
      type: Number,
      default: 600,
    },
    height: {
      type: Number,
      default: 250,
    },
  },
  data() {
    return {};
  },
  methods: {
    //弹窗关闭
    onClose() {
      this.$emit("onClose");
    },
    //弹窗确定
    onConfirm() {
      this.$emit("onConfirm");
    },
  },
};
</script>

<style lang="scss" scoped>
.containner{
  overflow-x: initial;
  overflow-y: auto;
}

.el-dialog__wrapper {
  ::v-deep .el-dialog {
    border-top-left-radius: 7px !important;
    border-top-right-radius: 7px !important;
    .el-dialog__header {
      border-top-left-radius: 7px !important;
      border-top-right-radius: 7px !important;
      background-color: #1890ff;
      .el-dialog__title {
        color: #fff;
        font-size: 15px;
        font-weight: 700;
      }
      .el-dialog__close {
        color: #fff;
      }
    }
    .el-dialog__body {
      padding: 10px 10px !important;
    }
    .el-dialog__footer {
      border-top: 1px solid #e8eaec !important;
      padding: 10px !important;
    }
  }
}
</style>
