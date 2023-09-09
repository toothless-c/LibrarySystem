<template>
  <el-main style="background-color: #f2f3f5">
    <div style="background-color: white;width: 100%;height: 120px;border-radius: 5px">
      <!--      <el-avatar  :size="100" src="https://img2.woyaogexing.com/2022/07/01/0a9359b8f23adfed!400x400.jpg">欢迎</el-avatar>-->
      <el-row :gutter="20">
        <el-col :span="8"><div class="grid-content bg-purple">
          <div style="display: flex; margin-top: 10px;"> <el-avatar  :size="100" src="https://img2.woyaogexing.com/2022/07/01/0a9359b8f23adfed!400x400.jpg"></el-avatar></div>
          <div v-if="grt=(ho<9?'早上好':
                    ho<12?'上午好':
                    ho<15?'中午好':
                    ho<19?'下午好':
                    '晚上好'
                    )" style="flex: 1; position: absolute;left: 130px;top: 50px; font-size: 1.25rem">
            {{grt}}{{username}}
          </div>
        </div>
        </el-col>
        <el-col :span="8"><div class="grid-content bg-purple">
        </div></el-col>
        <el-col :span="8"><div class="grid-content bg-purple">
          <div id="he-plugin-simple"></div>
        </div></el-col>
      </el-row>
    </div>
    <div style="display: flex;margin-top: 20px;background-color: white" >
      <div id="main1" style="width: 400px; height: 300px; flex-grow: 1"></div>
      <div id="main2" style="width: 400px; height: 300px; flex-grow: 1"></div>
    </div>
    <el-card class="box-card" style="margin-top:30px;">
      <div slot="header" class="clearfix">
        <span style="color:#000000;font-weight:600;">公告列表</span>
      </div>
      <div  v-for='(item,index) in gg' class="text item">
        {{ item.noticeTitle +"      "+ item.noticeContent }}
        <el-divider></el-divider>
      </div>
    </el-card>
  </el-main>
</template>

<script>
import {getweatherApi} from "@/api/index";
import {getOne} from "@/api/sanfang";
import {getListApi} from "@/api/notice";

export default {
  name: "Dashboard",
  data() {
    return {
      noticeParm: {
        currentPage: 0,
        pageSize: 4,
        noticeTitle: "",
        total: 0,
      },
      username:"小海",
      gg:[],
      ho:new Date().getHours()
    };
  },
  mounted() {
    this.myecharts1();
    this.myecharts2();

  },
  created() {
    this.getGg();
  },
  methods: {
    async getGg() {
      let res = await getListApi(this.noticeParm);
      this.gg =res.data.records
      console.log(this.gg)
    },
    myecharts1() {
      var myChart = this.$echarts.init(document.getElementById("main1"));
      // 指定图表的配置项和数据
      var option = {
        title: {
          text: "图书分类统计",
        },
        tooltip: {},
        legend: {
          data: ["分类"],
        },
        xAxis: {
          data: ["文学", "科技", "古典", "科普", "历史"],
        },
        yAxis: {},
        series: [
          {
            name: "销量",
            type: "bar",
            data: [5, 20, 36, 10, 15, 25],
          },
        ],
      };
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    },
    myecharts2() {
      var myChart = this.$echarts.init(document.getElementById("main2"));
      // 指定图表的配置项和数据
      var option = {
        title: {
          text: "热门图书排行榜",
          left: "center",
        },
        tooltip: {
          trigger: "item",
        },
        legend: {
          orient: "vertical",
          left: "left",
        },
        series: [
          {
            // name: "Access From",
            type: "pie",
            radius: ["40%", "70%"],
            avoidLabelOverlap: false,
            label: {
              show: false,
              position: "center",
            },
            emphasis: {
              label: {
                show: true,
                fontSize: "40",
                fontWeight: "bold",
              },
            },
            labelLine: {
              show: false,
            },
            data: [
              { value: 1048, name: "古典" },
              { value: 735, name: "科技" },
              { value: 580, name: "历史" },
              { value: 484, name: "科普" },
              { value: 300, name: "文学" },
            ],
          },
        ],
      };
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    },
  },
};
</script>

<style lang="scss" scoped>
.bottom-text {
  bottom: 0;
  width: 100%;
  background: rgba(0, 0, 0, 0.1);
  height: 25px;
  line-height: 25px;
  text-align: center;
  position: absolute;
  font-weight: 600;
}
.show-header {
  background: #00c0ef;
  color: #fff;
  height: 80px;
  border-radius: 5px;
  position: relative;
}
.show-num {
  font-size: 38px;
  font-weight: 600;
  padding: 5px;
}


.el-row {
  margin-bottom: 20px;
  &:last-child {
    margin-bottom: 0;
  }
}
.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  //background: #99a9bf;
}
.bg-purple {
  ////background: #d3dce6;
  //border: 1px solid #8a8787;
}
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
</style>
