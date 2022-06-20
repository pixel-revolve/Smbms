<template>
  <div class="app-container">
    <div class="block">
      <!-- 顶部搜索部分 -->
      <el-row :gutter="20">
        <el-col :span="4">
          <el-input
            v-model="listQuery.userId"
            size="mini"
            placeholder="请输入用户编号"
            @keyup.enter.native="search"
          />
        </el-col>
        <el-col :span="4">
          <el-input
            v-model="listQuery.goodsId"
            size="mini"
            placeholder="请输入商品编号"
            @keyup.enter.native="search"
          />
        </el-col>
        <el-col :span="4">
          <el-input
            v-model="listQuery.payMethod"
            size="mini"
            placeholder="请输入支付方式"
            @keyup.enter.native="search"
          />
        </el-col>
        <el-col :span="4">
          <el-input
            v-model="listQuery.deliverMethod"
            size="mini"
            placeholder="请输入运送方式"
            @keyup.enter.native="search"
          />
        </el-col>

        <el-col :span="4">
          <el-button
            type="success"
            size="mini"
            icon="el-icon-search"
            @click.native="search"
          >{{ $t("button.search") }}</el-button>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-refresh"
            @click.native="reset"
          >{{ $t("button.reset") }}</el-button>
        </el-col>
      </el-row>
      <br>
      <el-row>
        <div class="date">
          <span class="date_font">订购时间</span>
          <el-date-picker
            v-model="listQuery.orderStartDate"
            value-format='yyyy-MM-dd HH:mm:ss'
            format="yyyy-MM-dd HH:mm:ss"
            type="datetime"
            :picker-options="startOrderTime"
            placeholder="选择开始日期时间">
          </el-date-picker>
          -
            <el-date-picker
              type="datetime"
              placeholder="结束日期"
              :picker-options="endOrderTime"
              value-format='yyyy-MM-dd HH:mm:ss'
              format="yyyy-MM-dd HH:mm:ss"
              v-model="listQuery.orderEndDate"
            ></el-date-picker>
          <span class="date_font">需要日期</span>
          <el-date-picker
            v-model="listQuery.startDateNeeded"
            value-format='yyyy-MM-dd HH:mm:ss'
            format="yyyy-MM-dd HH:mm:ss"
            type="datetime"
            :picker-options="startNeededTime"
            placeholder="选择开始日期时间">
          </el-date-picker>
          -
            <el-date-picker
              type="datetime"
              placeholder="结束日期"
              :picker-options="endNeededTime"
              value-format='yyyy-MM-dd HH:mm:ss'
              format="yyyy-MM-dd HH:mm:ss"
              v-model="listQuery.endDateNeeded"
            ></el-date-picker>
        </div>
      </el-row>
      <br>
      <!-- 顶部按钮部分 -->
      <el-row>
        <el-col :span="24">
          <el-button
            type="success"
            size="mini"
            icon="el-icon-plus"
            @click.native="add"
          >
            {{ $t("button.add") }}
          </el-button>
        </el-col>
      </el-row>
    </div>
    <!-- 表格部分 -->
    <el-row>
      <el-col :span="24">
        <el-table
          v-loading="listLoading"
          :data="list"
          size="mini"
          element-loading-text="Loading"
          border
          fit
          highlight-current-row
          @current-change="handleCurrentChange"
        >
          <el-table-column label="用户编号">
            <template slot-scope="scope">
              <el-tag type="success" size="mini">
                {{ scope.row.userId }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="商品编号" align="left">
            <template slot-scope="scope">
              <el-tag type="danger" size="mini">
                {{ scope.row.goodsId }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="订购时间">
            <template slot-scope="scope">
              <div slot="reference" class="name-wrapper">
                <el-tag size="mini"> {{ scope.row.orderDate }} </el-tag>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="订购数量">
            <template slot-scope="scope">
              {{ scope.row.number }}
            </template>
          </el-table-column>
          <el-table-column label="需要日期">
            <template slot-scope="scope">
              <div slot="reference" class="name-wrapper">
                <el-tag size="mini"> {{ scope.row.dateNeeded }} </el-tag>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="支付方式">
            <template slot-scope="scope">
              <div slot="reference" class="name-wrapper">
                <el-tag size="mini"> {{ scope.row.payMethod }} </el-tag>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="运送方式">
            <template slot-scope="scope">
              <div slot="reference" class="name-wrapper">
                <el-tag size="mini"> {{ scope.row.deliverMethod }} </el-tag>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="操作" min-width="150px">
            <template slot-scope="scope">
              <el-button
                type="text"
                size="mini"
                icon="el-icon-edit"
                @click.native="editItem(scope.row)"
              >
                {{ $t("button.edit") }}
              </el-button>
              <el-button
                type="text"
                size="mini"
                icon="el-icon-delete"
                @click.native="removeItem(scope.row)"
              >
                {{ $t("button.delete") }}
              </el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-pagination
          background
          layout="total, sizes, prev, pager, next, jumper"
          :page-sizes="[5, 10, 20, 50, 100, 500]"
          :page-size="listQuery.limit"
          :total="total"
          :current-page.sync="listQuery.page"
          @size-change="changeSize"
          @current-change="fetchPage"
          @prev-click="fetchPrev"
          @next-click="fetchNext"
        />
      </el-col>
    </el-row>

<!-- 添加订单   -->
    <el-dialog :title="formTitle" :visible.sync="formVisible" width="70%">
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="120px"
        label-position="right"
      >
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户编号" prop="userId">
              <el-input v-model="form.userId" minlength="1" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="商品编号" prop="goodsId">
              <el-input v-model="form.goodsId" minlength="1"  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="订购时间" prop="orderDate">
              <el-input v-model="form.orderDate" minlength="1" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="订购数量" prop="number">
              <el-input v-model="form.number" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="需要日期" prop="dateNeeded">
              <el-input v-model="form.dateNeeded" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="支付方式" prop="payMethod">
              <el-input v-model="form.payMethod" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="运送方式" prop="deliverMethod">
              <el-input v-model="form.deliverMethod" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item>
          <el-button type="primary" @click="saveOrder">{{
            $t("button.submit")
          }}</el-button>
          <el-button @click.native="formVisible = false">{{
            $t("button.cancel")
          }}</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

<!--  修改订单  -->
    <el-dialog :title="editDialog.title" :visible.sync="editDialog.visible" width="70%">
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="120px"
        label-position="right"
      >
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户编号" prop="userId">
              <el-input v-model="form.userId" minlength="1" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="商品编号" prop="goodsId">
              <el-input v-model="form.goodsId" minlength="1"  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="订购时间" prop="orderDate">
              <el-input v-model="form.orderDate" minlength="1" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="订购数量" prop="number">
              <el-input v-model="form.number" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="需要日期" prop="dateNeeded">
              <el-input v-model="form.dateNeeded" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="支付方式" prop="payMethod">
              <el-input v-model="form.payMethod" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="运送方式" prop="deliverMethod">
              <el-input v-model="form.deliverMethod" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item>
          <el-button type="primary" @click="editOrder">{{
              $t("button.submit")
            }}</el-button>
          <el-button @click.native="editDialog.visible = false">{{
              $t("button.cancel")
            }}</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog title="角色分配" :visible.sync="roleDialog.visible" width="25%">
      <el-form>
        <el-row>
          <el-col :span="12">
            <el-tree
              ref="roleTree"
              :data="roleDialog.roles"
              show-checkbox
              node-key="roleId"
              :default-checked-keys="roleDialog.checkedRoleKeys"
              :props="roleDialog.defaultProps"
            />
          </el-col>
        </el-row>
        <el-form-item>
          <el-button type="primary" @click="setRole">{{
            $t("button.submit")
          }}</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script src="./order.js"></script>
<style rel="stylesheet/scss" lang="scss">
@import "src/styles/common.scss";
.avatar-uploader .el-upload {
border: 1px dashed #d9d9d9;
border-radius: 6px;
cursor: pointer;
position: relative;
overflow: hidden;
}
.avatar-uploader .el-upload:hover {
border-color: #409eff;
}
.avatar-uploader-icon {
font-size: 28px;
color: #8c939d;
width: 178px;
height: 178px;
line-height: 178px;
text-align: center;
}
.avatar {
width: 178px;
height: 178px;
display: block;
}
.el-date-editor {
  top: 10px;
}
.date_font {
  font-weight: lighter;
  color: #7d7d7f;
  font-size: 15px;
}
</style>

