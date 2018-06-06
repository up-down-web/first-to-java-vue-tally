<template>
  <div class="main">
    <div class="row">
      <Button type="primary" @click="onBack">返回</Button>
    </div>
    <Form ref="paymentForm" :model="formData" :label-width="100" :rules="rules">
      <FormItem label="日期" prop="paymentTime">
        <DatePicker v-model="formData.paymentTime" type="date" placeholder="消费日期" class="control" />
      </FormItem>
      <FormItem label="类别" prop="type">
        <Select v-model="formData.type" filterable class="control" placeholder="选择一个类别" >
          <Option v-for="item in typeDataSource" :value="item.value" :key="item.value">{{ item.label }}</Option>
        </Select>
      </FormItem>
      <FormItem
        label="新类别"
        v-show="formData.type===OTHER_TYPE"
        :required="formData.type===OTHER_TYPE"
        :rules="{required: formData.type===OTHER_TYPE, message: '请输入新类别', trigger: 'blur'}"
        prop="typeInput"
      >
        <Input v-model.trim="formData.typeInput" placeholder="新增类别" class="control" />
      </FormItem>
      <FormItem label="名称" prop="name">
        <Input v-model.trim="formData.name" placeholder="项目名称" class="control" />
      </FormItem>
      <FormItem label="金额" prop="charge">
        <Input v-model.number="formData.charge" placeholder="消费金额" class="control" />
      </FormItem>
      <FormItem label="地点" prop="site">
        <Input v-model.trim="formData.site" placeholder="消费地点:网站名称或线下地点" class="control"/>
      </FormItem>
      <FormItem label="备注" prop="remark">
        <Input v-model.trim="formData.remark" placeholder="其他备注" class="control"/>
      </FormItem>
      <FormItem>
        <Button type="success" @click="onSubmit('paymentForm')">提交</Button>
      </FormItem>
    </Form>
  </div>
</template>

<script>
  import _ from 'lodash'
  import { mapGetters, mapActions } from 'vuex'

  const OTHER_TYPE = Math.random() + '';

  export default {
    data () {
      return {
        OTHER_TYPE,
        id: 0,
        formData: {
          paymentTime: new Date(),
          type: '',
          typeInput: '',
          name: '',
          charge: '',
          site: '',
          remark: ''
        },
        rules: {
          paymentTime: [
            {required: true, type: 'date', message: '请选择日期', trigger: 'blur'}
          ],
          type: [
            {required: true, message: '请选择类别', trigger: 'blur'}
          ],
          name: [
            {required: true, message: '请输入名称', trigger: 'blur'},
            {type: 'string', max: 50, message: '名称长度不可超过50个字符', trigger: 'change'}
          ],
          charge: [
            {required: true, type: 'number', message: '请输入有效的金额', trigger: 'change'},
          ],
          site: [
            {required: true, message: '请输入地点', trigger: 'blur'}
          ]
        }
      }
    },
    computed: {
      ...mapGetters({typeList: 'typeList', paymentTable: 'paymentTable'}),
      typeDataSource() {
        const typeList = this.typeList;
        return [{label: '其他', value: OTHER_TYPE}, ...typeList];
      }
    },
    methods: {
      onBack() {
        this.$router.push({name: 'home'});
      },
      ...mapActions({
        submitNewPayment: 'submitNewPayment'
      }),
      onSubmit(form) {
        this.$refs[form].validate(async (valid) => {
          if (!valid) {
            return;
          }

          const payment = {...this.formData, id: this.id};
          if (payment.type === OTHER_TYPE) {
            payment.type = payment.typeInput;
            delete payment.typeInput;
          }

          try {
            await this.submitNewPayment(payment);
          }
          catch(e) {
            this.$Message.error('提交失败');
            return;
          }

          this.$router.push({name: 'home'});
        })
      }
    },
    async created () {
      await this.$store.dispatch('loadTypeList')
      const id = this.$route.params.id;
      if (!isNaN(id)) {
        const payment = _.find(this.paymentTable.data, {id});
        const oldData = _.pick(payment, _.keys(this.formData));
        oldData.paymentTime = new Date(oldData.paymentTime);
        this.formData = oldData;
        this.id = id;
      }
    }
  }
</script>

<style scoped lang="less">
  .main {
    .row {
      display: block;
      margin-bottom: 20px;
    }

    .control {
      width: 300px;
    }
  }
</style>