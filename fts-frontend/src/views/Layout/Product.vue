<template>
  <div class="product-list">
    <h2>产品列表</h2>

    <div style="text-align: center;">
      <el-input class="input" v-model="searchKeyword" placeholder="请输入关键词" />
      <el-button class="inputbutton" @click="handleSearch">搜索</el-button>
    </div>

    <ul class="product-list">
      <li v-for="product in paginatedProducts" :key="product.productId" @click="showProductDetails(product)" class="product-item">
        <div class="product-details">
          <p>{{ product.productName }}</p>
          <p>产品类型：{{ product.productType || '未指定' }}</p>
          <p>风险等级：{{ product.riskLevel !== undefined ? product.riskLevel : '无信息' }}</p>
        </div>
      </li>
    </ul>

    <div class="pagination-container">
      <el-pagination
          layout="prev, pager, next"
          :total="totalProducts"
          :page-size="pageSize"
          :current-page="currentPage"
          @current-change="handlePageChange"
      />
    </div>

    <!-- Product Details Dialog -->
    <el-dialog
        v-model="dialogVisible"
        :title="selectedProduct ? selectedProduct.productName : '产品详情'"
        custom-class="custom-dialog"
        width="50%"
        top="15vh"
        @close="clearSelectedProduct"
    >
      <div v-if="selectedProduct" class="product-details-content">
        <dl class="product-info">
          <dt>产品名称：</dt><dd>{{ selectedProduct.productName }}</dd>
          <dt>产品类型：</dt><dd>{{ selectedProduct.productType || '未指定' }}</dd>
          <dt>风险等级：</dt><dd>{{ selectedProduct.riskLevel !== undefined ? Level(selectedProduct.riskLevel) : '无信息' }}</dd>
          <dt>产品状态：</dt><dd>{{ selectedProduct.productStatus || '未指定' }}</dd>
          <dt>净值：</dt><dd>{{ selectedProductNetValue !== null ? selectedProductNetValue : '无信息' }}</dd>
        </dl>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, computed, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import { fetchProducts, searchProducts,fetchNetValue,fetchTransactionDate } from '@/api/product';
import type { Product } from '@/types/product';

export default defineComponent({
  name: 'ProductList',
  setup() {
    const products = ref<Product[]>([]);
    const totalProducts = ref(0);
    const currentPage = ref(1);
    const pageSize = ref(10);
    const searchKeyword = ref('');
    const appliedSearchKeyword = ref(''); // 新增的ref用于保存实际应用的搜索关键词
    const dialogVisible = ref(false);
    const selectedProduct = ref<Product | null>(null);

    const selectedProductNetValue = ref<number | null>(null); // 新增的净值信息
    const transactionDate = ref<string | null>(null); // 新增：存储交易日期

    // 更新 paginatedProducts 的计算属性
    const paginatedProducts = computed(() => {
      let filteredProducts = products.value;

      if (appliedSearchKeyword.value) {
        filteredProducts = products.value.filter((product) =>
            product.productName.toLowerCase().includes(appliedSearchKeyword.value.toLowerCase())
        );
      }

      return filteredProducts.slice((currentPage.value - 1) * pageSize.value, currentPage.value * pageSize.value);
    });

    const handleSearch = async () => {
      if (searchKeyword.value.trim()) {
        try {
          const res = await searchProducts(searchKeyword.value);
          products.value = res.data as Product[];
          totalProducts.value = products.value.length;
          appliedSearchKeyword.value = searchKeyword.value; // 应用搜索关键词
        } catch (error) {
          ElMessage.error('搜索失败');
        }
      } else {
        loadProducts();
        appliedSearchKeyword.value = ''; // 清除应用的搜索关键词
      }
    };

    const loadProducts = async () => {
      try {
        const res = await fetchProducts(currentPage.value, pageSize.value);
        products.value = res.data as Product[];
        totalProducts.value = products.value.length;
        appliedSearchKeyword.value = ''; // 清除应用的搜索关键词
      } catch (error) {
        ElMessage.error('加载产品失败');
      }
    };

    const showProductDetails = async (product: Product) => {
      console.log('Clicked product:', product); // 调试信息
      selectedProduct.value = product;
      try {
        const res = await fetchNetValue(product.productId, '2024-12-16');
        selectedProductNetValue.value = res.data; // 假设返回的数据结构是直接的净值值
      } catch (error) {
        ElMessage.error('获取净值失败');
        selectedProductNetValue.value = null;
      }
      dialogVisible.value = true;
    };
    const clearSelectedProduct = () => {
      selectedProduct.value = null;
    };

    const handlePageChange = (newPage: number) => {
      currentPage.value = newPage;
      loadProducts();
    };

    const getTransactionDate = async () => {
      try {
        const res = await fetchTransactionDate();
        transactionDate.value = res.data as string;
      } catch (error) {
        ElMessage.error('获取交易日期失败');
        transactionDate.value = null;
      }
    };

  const Level = (level : number) => {
    switch (level) {
      case 0:
        return '低风险型';
      case 1:
        return '中低风险型';
      case 2:
        return '中风险型';
      case 3:
        return '中高风险型';
      default:
        return '高风险型';
    }
  }

    onMounted(() => {
      loadProducts();
      appliedSearchKeyword.value = ''; // 确保初始状态下没有应用的搜索关键词
      getTransactionDate(); // 页面加载时获取交易日期
    });

    return {
      products,
      totalProducts,
      currentPage,
      pageSize,
      searchKeyword,
      appliedSearchKeyword,
      dialogVisible,
      selectedProduct,
      paginatedProducts,
      handleSearch,
      showProductDetails,
      clearSelectedProduct,
      handlePageChange,
      Level,
      selectedProductNetValue,
      transactionDate, // 返回给模板
    };
  },
});
</script>

<style scoped>
.product-list {
  list-style-type: none;
  padding: 0;
  margin: 0 auto; /* 居中 */
  max-width: 80%; /* 设置最大宽度 */
}

.product-item {
  margin: 5px 0;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  cursor: pointer;
  background-color: #fff;
  transition: background-color 0.3s;
}

.product-item:hover {
  background-color: #f0f0f0;
}

.product-details {
  display: flex;
  align-items: center; /* 垂直居中对齐 */
}

.product-details p {
  margin: 0;
  flex: 1;
  padding: 0 10px;
}

.input {
  width: 70%; /* 设置为父容器宽度的70% */
}

/* 居中标题和搜索框 */
.product-list h2,
.product-list .el-input,
.product-list .el-button {
  text-align: center;
  margin: 20px auto;
}

.inputbutton {
  background-color: #409EFF; /* 设置背景颜色 */
  border: none;              /* 移除默认边框 */
  color: white;              /* 设置文本颜色 */
  padding: 10px 20px;        /* 设置内边距 */
  font-size: 16px;           /* 设置字体大小 */
  cursor: pointer;           /* 鼠标悬停时显示手型 */
  border-radius: 4px;        /* 设置圆角 */
  transition: background-color 0.3s, transform 0.3s; /* 添加过渡效果 */
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* 添加阴影 */
}

/* 按钮悬停样式 */
.inputbutton:hover {
  background-color: #66B1FF; /* 更改悬停时的背景颜色 */
  transform: translateY(-2px); /* 添加轻微提升效果 */
}

/* 按钮点击样式 */
.inputbutton:active {
  background-color: #3A8EE6; /* 更改点击时的背景颜色 */
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.2); /* 减轻阴影 */
  transform: translateY(1px); /* 轻微下沉效果 */
}

.pagination-container {
  display: grid;
  place-items: center; /* 同时水平和垂直居中 */
  margin-top: 20px;    /* 根据需要调整上下边距 */
  margin-bottom: 20px;
}

/* 弹窗自定义样式 */
.custom-dialog {
  border-radius: 10px;
}

.custom-dialog .el-dialog__header {
  background-color: #409EFF;
  color: white;
  padding: 16px;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
}

.custom-dialog .el-dialog__title {
  font-size: 18px;
}

.custom-dialog .el-dialog__body {
  padding: 20px;
}

.product-details-content {
  text-align: center; /* 居中文本 */
  display: flex;
  flex-direction: column;
}

.product-info {
  display: grid;
  grid-template-columns: 1fr 2fr;
  gap: 10px;
  font-size: 1.3em; /* 设置按钮文本的字体大小 */
}

.product-info dt {
  font-weight: bold;
  color: #555;
}

.product-info dd {
  margin: 0;
}

.dialog-footer {
  text-align: right;
}

.dialog-footer .el-button {
  background-color: #409EFF;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  transition: background-color 0.3s, transform 0.3s;
}

.dialog-footer .el-button:hover {
  background-color: #66B1FF;
  transform: translateY(-2px);
}

.dialog-footer .el-button:active {
  background-color: #3A8EE6;
  transform: translateY(1px);
}
</style>
