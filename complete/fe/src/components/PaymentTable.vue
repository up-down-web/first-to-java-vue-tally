<template>
  <div>
    <Button type="primary" @click="onAdd" class="btn-add">新增</Button>
    <Table :columns="columnConfig" :data="paymentTable.data"></Table>
    <div style="margin: 10px;overflow: hidden">
      <div style="float: right;">
        <Page
          :total="paymentTable.totalCount"
          :current="paymentTable.pageNumber + 1"
          :page-size="paymentTable.pageSize"
          @on-change="onChangePage"
          show-total
        ></Page>
      </div>
    </div>
    <Modal
      v-model="isShowDetail"
      title="消费详情"
    >
      <div slot="footer">
        <Button type="default" @click="isShowDetail=false">取消</Button>
        <Button type="warning" @click="$router.push({name: 'editor', params: {id: detail.id}})">修改</Button>
      </div>
      <p>
        日期：{{detail.paymentTime}}
      </p>
      <p>
        类别：{{detail.type}}
      </p>
      <p>
        名称：{{detail.name}}
      </p>
      <p>
        金额：{{detail.charge}}
      </p>
      <p>
        地点：{{detail.site}}
      </p>
      <p>
        备注：{{detail.remark || '-'}}
      </p>
      <p>
        添加时间：{{detail.createdAt}}
      </p>
      <p>
        修改时间：{{detail.updatedAt}}
      </p>
    </Modal>
  </div>
</template>

<script>
  import PaymentTableActions from './PaymentTableActions.vue'
  import { mapGetters, mapActions } from 'vuex'
  import moment from 'moment'

  export default {
    name: 'HelloWorld',
    computed: mapGetters({
      paymentTable: 'paymentTable'

    }),
    methods: {
      ...mapActions({
        deletePayment: 'deletePayment',
        changePaymentPage: 'changePaymentPage'
      }),
      onDelete(row) {
        this.$Modal.confirm({
          title: '温馨提示',
          content: `确认删除 ${row.name} 吗？`,
          onOk: () => {
            this.deletePayment(row.id);
          }
        });
      },
      onAdd() {
        this.$router.push({name: 'editor'});
      },
      onView(row) {
        const datetimeFormat = 'YYYY-MM-DD HH:mm'
        this.detail = {
          ...row,
          createdAt: moment(row.createdAt).format(datetimeFormat),
          updatedAt: moment(row.updatedAt).format(datetimeFormat),
        }
        this.isShowDetail = true
      },
      onChangePage(page) {
        this.changePaymentPage(page - 1);
      }
    },
    created () {
      this.$store.dispatch('loadPaymentData')
    },
    data () {
      return {
        isShowDetail: false,
        detail: {},
        columnConfig: [
          {
            title: '日期',
            key: 'paymentTime'
          },
          {
            title: '类别',
            key: 'type'
          },
          {
            title: '项目',
            key: 'name'
          },
          {
            title: '金额',
            key: 'charge'
          },
          {
            title: '地点',
            key: 'site'
          },
          {
            title: '操作',
            key: 'Action',
            render: (h, {row}) => h(PaymentTableActions, {
              props: {
                onView: () => this.onView(row),
                onDelete: () => this.onDelete(row)
              }
            })
          }
        ]
      }
    }
  }
</script>

<style scoped lang="less">
  .btn-add {
    margin-bottom: 20px;
  }
</style>
