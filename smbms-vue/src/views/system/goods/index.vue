<template>
<div class="app-container">
<div class="block">
    <!-- 顶部搜索部分 -->
    <el-row :gutter="20">
    <el-col :span="4">
        <el-input
        v-model="listQuery.name"
        prop:
        size="mini"
        placeholder="请输入商品名"
        @keyup.enter.native="search"
        ></el-input>
    </el-col>
    <el-col :span="4">
      <el-select v-model="price" clearable placeholder="请选择价格" @keyup.enter.native="search">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
    </el-col>
    <el-col :span="4">
        <el-input
        v-model="listQuery.brand"
        size="mini"
        placeholder="请输入品牌"
        @keyup.enter.native="search"
        ></el-input>
    </el-col>

    <el-col :span="4">
        <el-button
        type="success"
        size="mini"
        icon="el-icon-search"
        @click.native="search"
        >{{ $t("button.search") }}
        </el-button>
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
        <el-table-column label="商品名">
        <template slot-scope="scope">
            <el-tag type="success" size="mini">
            {{ scope.row.name }}
            </el-tag>
        </template>
        </el-table-column>
        <el-table-column label="价格" align="left">
        <template slot-scope="scope">
            <el-tag type="danger" size="mini">
                {{ scope.row.price }}
            </el-tag>
        </template>
        </el-table-column>
        <el-table-column label="简介">
        <template slot-scope="scope">
            <div slot="reference" class="name-wrapper">
                <el-tag size="mini"> {{ scope.row.comment }} </el-tag>
            </div>
        </template>
        </el-table-column>
        <el-table-column label="品牌">
        <template slot-scope="scope">
            {{ scope.row.brand }}
        </template>
        </el-table-column>
        <el-table-column label="供应商编号">
        <template slot-scope="scope">
            {{ scope.row.providerId || "未绑定供应商" }}
        </template>
        </el-table-column>
        <el-table-column label="库存">
        <template slot-scope="scope">
            {{ scope.row.stock }}
        </template>
        </el-table-column>
        <el-table-column label="描述图片">
        <template slot-scope="scope">
            <el-image
            :src="scope.row.coverPic"
            :preview-src-list="[scope.row.coverPic]"
            >></el-image
            >
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
<!-- 增加商品表格  -->
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
        <el-form-item label="商品名" prop="name">
            <el-input v-model="form.name" minlength="1"></el-input>
        </el-form-item>
        </el-col>
        <el-col :span="12">
        <el-form-item label="价格" prop="price">
            <el-input v-model="form.price" minlength="1"></el-input>
        </el-form-item>
        </el-col>
        <el-col :span="12">
        <el-form-item label="简介" prop="comment">
            <el-input v-model="form.comment" minlength="1"></el-input>
        </el-form-item>
        </el-col>
        <el-col :span="12">
        <el-form-item label="品牌" prop="brand">
            <el-input v-model="form.brand"></el-input>
        </el-form-item>
        </el-col>
        <el-col :span="12">
        <el-form-item label="供应商编号" prop="providerId">
            <el-input v-model="form.providerId"></el-input>
        </el-form-item>
        </el-col>
        <el-col :span="12">
        <el-form-item label="库存" prop="stock">
            <el-input v-model="form.stock"></el-input>
        </el-form-item>
        </el-col>
        <el-col :span="12">
        <el-form-item label="封面图" prop="coverPic">
            <el-upload
            class="avatar-uploader"
            v-loading="coverPicloading"
            action="http://10.15.120.205:8080/api/upload/image"
            name="imgFile"
            :headers="token"
            :on-success="handleCoverPicSuccess"
            :show-file-list="false"
            :before-upload="(file) => beforeImgUpload(file, 'coverPic')"
            >
            <img v-if="form.coverPic" :src="form.coverPic" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
        </el-form-item>
        </el-col>
    </el-row>
    <el-form-item>
        <el-button type="primary" @click="saveGoods">{{
        $t("button.submit")
        }}</el-button>
        <el-button @click.native="formVisible = false">{{
        $t("button.cancel")
        }}</el-button>
    </el-form-item>
    </el-form>
</el-dialog>

<!-- 修改商品表格  -->
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
          <el-form-item label="商品名" prop="name">
            <el-input v-model="form.name" minlength="1"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="价格" prop="price">
            <el-input v-model="form.price" minlength="1"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="简介" prop="comment">
            <el-input v-model="form.comment" minlength="1"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="品牌" prop="brand">
            <el-input v-model="form.brand"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="供应商编号" prop="providerId">
            <el-input v-model="form.providerId"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="库存" prop="stock">
            <el-input v-model="form.stock"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="封面图" prop="coverPic">
            <el-upload
              class="avatar-uploader"
              v-loading="coverPicloading"
              action="http://10.15.120.205:8080/api/upload/image"
              name="imgFile"
              :headers="token"
              :on-success="handleCoverPicSuccess"
              :show-file-list="false"
              :before-upload="(file) => beforeImgUpload(file, 'coverPic')"
            >
              <img v-if="form.coverPic" :src="form.coverPic" class="avatar" />
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item>
        <el-button type="primary" @click="editGoods">{{
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

<script src="./goods.js"></script>
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

