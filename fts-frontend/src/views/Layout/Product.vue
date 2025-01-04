<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import { fetchProducts, searchProducts, fetchNetValue, fetchTransactionDate } from '@/api/product';
import type { Product } from '@/types/product';
import { useRoute, useRouter } from 'vue-router';

const searchKeyword = ref('');
const products = ref<Product[]>([]);
const totalProducts = ref(0);
const currentPage = ref(1);
const pageSize = ref(10);
const appliedSearchKeyword = ref('');
const dialogVisible = ref(false);
const selectedProduct = ref<Product | null>(null);
const selectedProductNetValue = ref<number | null>(null);
const transactionDate = ref<string | null>(null);
const router = useRouter();
const route = useRoute();

// Update paginatedProducts as computed property
const paginatedProducts = computed(() => {
  let filteredProducts = products.value;
  if (appliedSearchKeyword.value) {
    filteredProducts = products.value.filter((product) =>
      product.productName.toLowerCase().includes(appliedSearchKeyword.value.toLowerCase())
    );
  }
  return filteredProducts.slice((currentPage.value - 1) * pageSize.value, currentPage.value * pageSize.value);
});

// Search handler
const handleSearch = async () => {
  if (searchKeyword.value.trim()) {
    try {
      const res = await searchProducts(searchKeyword.value);
      products.value = res.data as Product[];
      totalProducts.value = products.value.length;
      appliedSearchKeyword.value = searchKeyword.value;
    } catch (error) {
      ElMessage.error('搜索失败');
    }
  } else {
    loadProducts();
    appliedSearchKeyword.value = '';
  }
};

// Load products
const loadProducts = async () => {
  try {
    const res = await fetchProducts(currentPage.value, pageSize.value);
    products.value = res.data as Product[];
    totalProducts.value = products.value.length;
    appliedSearchKeyword.value = '';
  } catch (error) {
    ElMessage.error('加载产品失败');
  }
};

// Show product details
const showProductDetails = async (product: Product) => {
  selectedProduct.value = product;
  try {
    if(!product.productId || !transactionDate.value){
      return ElMessage.error('获取交易日期失败');
    }
    const res = await fetchNetValue(product.productId, transactionDate.value);
    selectedProductNetValue.value = res.data;
  } catch (error) {
    ElMessage.error('获取净值失败');
    selectedProductNetValue.value = null;
  }
  dialogVisible.value = true;
};

// Clear selected product
const clearSelectedProduct = () => {
  selectedProduct.value = null;
};

// Handle page change
const handlePageChange = (newPage: number) => {
  currentPage.value = newPage;
  loadProducts();
};

// Get transaction date
const getTransactionDate = async () => {
  try {
    const res = await fetchTransactionDate();
    transactionDate.value = res.data as string;
  } catch (error) {
    ElMessage.error('获取交易日期失败');
    transactionDate.value = null;
  }
};

// Level function
const Level = (level:number | undefined) => {
  switch (level) {
    case 0: return '低风险型';
    case 1: return '中低风险型';
    case 2: return '中风险型';
    case 3: return '中高风险型';
    default: return '高风险型';
  }
};

onMounted(() => {
  loadProducts();
  getTransactionDate();
});

const subscription = (productId:number,productName:string) => {
  router.push({
    path: '/subscription',
    query: {
      productId,
      productName,
    },
  });
};

const redemption = (productId:number,productName:string) => {
  router.push({
    path: '/redemption',
    query: {
      productId,
      productName,
    },
  });
};
</script>

<template>
  <el-card style="width: 99.9%; height: 99.8%;">
    <!-- 搜索框 -->
    <div style="text-align: center; margin-bottom: 20px;">
      <el-input class="input" v-model="searchKeyword" placeholder="请输入关键词" style="width: 70%;" />
      <el-button class="inputbutton" @click="handleSearch" style="margin-left: 20px;" type="primary">搜索</el-button>
    </div>

    <!-- 表格 -->
    <div class="table-container">
      <el-table :data="paginatedProducts" style="width: 100%;">
        <el-table-column prop="productName" label="产品名称" width="400" align="center"/>
        <el-table-column prop="productType" label="产品类型" width="400" align="center"/>
        <el-table-column prop="riskLevel" label="风险等级" width="300" align="center">
          <template #default="scope">
            <span>{{ Level(scope.row.riskLevel) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="300" align="center">
          <template #default="scope">
            <el-button @click="showProductDetails(scope.row)" type="text">查看详情</el-button>
            <el-button @click="subscription(scope.row.productId,scope.row.productName)" type="text">申购</el-button>
            <el-button @click="redemption(scope.row.productId,scope.row.productName)" type="text">赎回</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页 -->
    <div class="pagination-container" style="margin-top: 20px; display: flex; justify-content: center;">
      <el-pagination
        layout="prev, pager, next"
        :total="totalProducts"
        :page-size="pageSize"
        :current-page="currentPage"
        @current-change="handlePageChange"
        background
      />
    </div>

    <!-- 产品详情对话框 -->
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
          <dt>风险等级：</dt><dd>{{ Level(selectedProduct.riskLevel) }}</dd>
          <dt>净值：</dt><dd>{{ selectedProductNetValue !== null ? selectedProductNetValue : '无信息' }}</dd>
        </dl>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">关闭</el-button>
        </span>
      </template>
    </el-dialog>
  </el-card>
</template>

<style scoped>
.table-container {
  margin-top: 20px;
}

.product-details-content {
  text-align: center;
  display: flex;
  flex-direction: column;
}

.product-info {
  display: grid;
  grid-template-columns: 1fr 2fr;
  gap: 10px;
  font-size: 1.3em;
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
