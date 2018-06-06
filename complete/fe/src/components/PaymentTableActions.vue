<template>
  <div>
    <Button type="primary" @click="onView" style="margin-right: 5px;">查看</Button>
    <Button type="error" @click="onDelete">删除</Button>
  </div>
</template>

<script>
  export default {
    name: 'PaymentTableActions',
    props: {
      onView: Function,
      onDelete: Function
    }
  }
</script>