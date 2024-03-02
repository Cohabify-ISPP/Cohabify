<template>
    <div>
      <button type="button" class="btn btn-primary" @click.prevent="handleClick(tag)" v-for="tag in tags" :key="tag">
        Click aquí {{ tag }}
      </button>
    </div>
</template>
  
  <script>
  export default {
    props: ['tags']
  };
  </script>
  