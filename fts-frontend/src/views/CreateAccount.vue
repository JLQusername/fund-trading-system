<script lang="ts" setup>
import { ref } from 'vue';
import { ElForm, ElFormItem, ElInput, ElButton, ElMessageBox, ElMessage } from 'element-plus';
import { sendCodeService } from '@/api/login';
import { useRouter } from 'vue-router';
import type { CustomerDTO } from '@/types/account';
import RiskAssessment from '@/components/RiskAssessment.vue';

// 表单数据模型
const form = ref({
  phoneNumber: '',
  password: '',
  idNumber: '',
  name:'',
  riskLevel:0,
} as CustomerDTO);

const confirmPassword = ref('');
const code = ref('');
const router = useRouter();
const isTestLevel = ref(true);

// 表单验证规则
const rules = {
  phoneNumber: [
    { required: true, message: '手机号不能为空', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入有效的手机号', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '密码不能为空', trigger: 'blur' },
    { min: 3, max: 16, message: '密码长度在6到16位之间', trigger: 'blur' },
  ],
  idNumber: [
    { required: true, message: '身份证号码不能为空', trigger: 'blur' },
    { pattern: /^\d{17}(\d|X|x)$/, message: '请输入有效的身份证号码', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
  ],
  name: [
    { required: true, message: '姓名不能为空', trigger: 'blur' },
  ],
  code: [
    { required: true, message: '请输入验证码', trigger: 'blur' },
  ]
};
const formRef = ref();
const handleSubmit = () => {
  formRef.value.validate((valid: boolean) => {
    if (valid) {
      // 执行提交逻辑，比如调用API
      console.log('表单验证通过，提交数据：', form.value);
    } else {
      console.log('表单验证失败，请检查输入');
      return false;
    }
  });
};

const handleLevelChange = (level: number) => {
  form.value.riskLevel = level;
};

</script>

<template>
  <div class="create-account-container">
    <h1 style="padding: 50px; text-align: center; color: #0b407ce0;">同济基金交易系统 开户界面</h1>
    <el-form :model="form" :rules="rules" ref="formRef" label-width="120px" class="form-container">
      <el-form-item label="姓名" prop="name">
        <el-input v-model="form.name" placeholder="请输入姓名"></el-input>
      </el-form-item>
      <el-form-item label="手机号" prop="phoneNumber">
        <el-input v-model="form.phoneNumber" placeholder="请输入手机号"></el-input>
      </el-form-item>
      <el-form-item label="验证码" prop="code">
        <el-input v-model="code" placeholder="请输入验证码">
          <template #append>
            <el-button @click="sendCodeService(form.phoneNumber)" type="primary" size="small">发送验证码</el-button>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item label="身份证号码" prop="idNumber">
        <el-input v-model="form.idNumber" placeholder="请输入身份证号码"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="form.password" placeholder="请输入密码" type="password" maxlength="16"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input v-model="confirmPassword" placeholder="请确认密码" type="password" ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="danger" @click="isTestLevel = !isTestLevel">{{isTestLevel ? "跳过风险评估" : "进行风险评估（可跳过）"}}</el-button>
      </el-form-item>
      <div v-if="isTestLevel">
        <RiskAssessment @update:level="handleLevelChange" style="margin-left: 110px;"/>
      </div>
      <el-form-item>
        <el-button type="primary" @click="handleSubmit" style="width: 15%; margin-top: 20px;">确定开户</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<style scoped>
.create-account-container {
  padding: 2px;
  background-color: #cad1d82e;
  min-height: 100vh
}

.form-container {
  margin-left: 40px;
  width: 900px;
}
</style>