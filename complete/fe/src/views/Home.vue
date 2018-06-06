<template>
  <div class="home">
    <PaymentTable />
  </div>
</template>

<script>
import PaymentTable from '@/components/PaymentTable.vue'

export default {
  name: 'home',
  components: {
    PaymentTable
  }
}
</script>