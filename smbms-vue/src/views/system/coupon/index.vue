<template>
<div class="app-container">
<div class="block">
    <!-- 顶部搜索部分 -->
    <el-row :gutter="20">
    <el-col :span="4">
        <el-input
        v-model="listQuery.price"
        size="mini"
        placeholder="请输入优惠价格"
        @keyup.enter.native="search"
        ></el-input>
    </el-col>
    <el-col :span="4">
        <el-input
        v-model="listQuery.userId"
        size="mini"
        placeholder="请输入用户编号"
        @keyup.enter.native="search"
        ></el-input>
    </el-col>

    <el-col :span="4">
        <el-button
        type="success"
        size="mini"
        icon="el-icon-search"
        @click.native="search"
        >{{ $t("button.search") }}</el-button
        >
        <el-button
        type="primary"
        size="mini"
        icon="el-icon-refresh"
        @click.native="reset"
        >{{ $t("button.reset") }}</el-button
        >
    </el-col>
    </el-row>
    <br />
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
        :data="list"
        size="mini"
        v-loading="listLoading"
        element-loading-text="Loading"
        border
        fit
        highlight-current-row
        @current-change="handleCurrentChange"
    >
        <el-table-column label="优惠价格">
        <template slot-scope="scope">
            <el-tag type="success" size="mini">
            {{ scope.row.price }}
            </el-tag>
        </template>
        </el-table-column>
        <el-table-column label="描述" align="left">
        <template slot-scope="scope">
            <el-tag type="danger" size="mini">
                {{ scope.row.comment }}
            </el-tag>
        </template>
        </el-table-column>
        <el-table-column label="持有用户编号">
        <template slot-scope="scope">
            <div slot="reference" class="name-wrapper">
                <el-tag size="mini"> {{ scope.row.userId }} </el-tag>
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
    >
    </el-pagination>
    </el-col>
</el-row>
<!-- 添加优惠券表格 -->
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
        <el-form-item label="优惠价格" prop="price">
            <el-input v-model="form.price" minlength="1"></el-input>
        </el-form-item>
        </el-col>
        <el-col :span="12">
        <el-form-item label="描述" prop="comment">
            <el-input v-model="form.comment" minlength="1"></el-input>
        </el-form-item>
        </el-col>
        <el-col :span="12">
        <el-form-item label="持有用户编号" prop="userId">
            <el-input v-model="form.userId" minlength="1"></el-input>
        </el-form-item>
        </el-col>
    </el-row>
    <el-form-item>
        <el-button type="primary" @click="saveCoupon">{{
        $t("button.submit")
        }}</el-button>
        <el-button @click.native="formVisible = false">{{
        $t("button.cancel")
        }}</el-button>
    </el-form-item>
    </el-form>
</el-dialog>

  <!-- 修改优惠券表格  -->
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
          <el-form-item label="优惠价格" prop="price">
            <el-input v-model="form.price" minlength="1"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="描述" prop="comment">
            <el-input v-model="form.comment" minlength="1"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="持有用户编号" prop="userId">
            <el-input v-model="form.userId" minlength="1"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item>
        <el-button type="primary" @click="editCoupon">{{
            $t("button.submit")
          }}</el-button>
        <el-button @click.native="editDialog.visible = false">{{
            $t("button.cancel")
          }}</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</div>
</template>

<script src="./coupon.js"></script>
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
</style>

