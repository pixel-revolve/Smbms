<template>
<div class="app-container">
<div class="block">
    <!-- 顶部搜索部分 -->
    <el-row :gutter="20">
    <el-col :span="4">
        <el-input
        v-model="listQuery.code"
        size="mini"
        placeholder="请输入编号"
        @keyup.enter.native="search"
        ></el-input>
    </el-col>
    <el-col :span="4">
        <el-input
        v-model="listQuery.name"
        size="mini"
        placeholder="请输入供应商名"
        @keyup.enter.native="search"
        ></el-input>
    </el-col>
    <el-col :span="4">
      <el-input
        v-model="listQuery.desc"
        size="mini"
        placeholder="请输入描述"
        @keyup.enter.native="search"
      ></el-input>
    </el-col>
    <el-col :span="4">
        <el-input
        v-model="listQuery.phone"
        size="mini"
        placeholder="请输入手机号"
        @keyup.enter.native="search"
        ></el-input>
    </el-col>
    <el-col :span="4">
      <el-input
        v-model="listQuery.address"
        size="mini"
        placeholder="请输入地址"
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
        <el-table-column label="编号">
        <template slot-scope="scope">
            <el-tag type="success" size="mini">
            {{ scope.row.code }}
            </el-tag>
        </template>
        </el-table-column>
        <el-table-column label="供应商名" align="left">
        <template slot-scope="scope">
            <el-tag type="danger" size="mini">
                {{ scope.row.name }}
            </el-tag>
        </template>
        </el-table-column>
        <el-table-column label="描述">
        <template slot-scope="scope">
            <div slot="reference" class="name-wrapper">
                <el-tag size="mini"> {{ scope.row.desc }} </el-tag>
            </div>
        </template>
        </el-table-column>
        <el-table-column label="手机">
        <template slot-scope="scope">
            {{ scope.row.phone || "未绑定手机" }}
        </template>
        </el-table-column>
        <el-table-column label="地址">
        <template slot-scope="scope">
            {{ scope.row.address }}
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

<!--  增加供应商    -->
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
        <el-form-item label="编号" prop="code">
            <el-input v-model="form.code" minlength="1"></el-input>
        </el-form-item>
        </el-col>
        <el-col :span="12">
        <el-form-item label="供应商名" prop="name">
            <el-input v-model="form.name" minlength="1"></el-input>
        </el-form-item>
        </el-col>
        <el-col :span="12">
        <el-form-item label="描述" prop="desc">
            <el-input v-model="form.desc" minlength="1"></el-input>
        </el-form-item>
        </el-col>
        <el-col :span="12">
        <el-form-item label="手机" prop="phone">
            <el-input v-model="form.phone"></el-input>
        </el-form-item>
        </el-col>
        <el-col :span="12">
        <el-form-item label="地址" prop="address">
            <el-input v-model="form.address"></el-input>
        </el-form-item>
        </el-col>
    </el-row>
    <el-form-item>
        <el-button type="primary" @click="saveProvider">{{
        $t("button.submit")
        }}</el-button>
        <el-button @click.native="formVisible = false">{{
        $t("button.cancel")
        }}</el-button>
    </el-form-item>
    </el-form>
</el-dialog>

  <!-- 修改供应商表格  -->
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
          <el-form-item label="编号" prop="code">
            <el-input v-model="form.code" minlength="1"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="供应商名" prop="name">
            <el-input v-model="form.name" minlength="1"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="描述" prop="desc">
            <el-input v-model="form.desc" minlength="1"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="手机" prop="phone">
            <el-input v-model="form.phone"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="地址" prop="address">
            <el-input v-model="form.address"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item>
        <el-button type="primary" @click="editProvider">{{
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

<script src="./provider.js"></script>
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

