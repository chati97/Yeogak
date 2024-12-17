<template>
    <div>
        <h1 class="underline">게시판</h1>
        <router-view class="content px-3 rounded bg-white"></router-view>
    </div>
</template>

<script>
export default {
    name: 'BoardView',
    components: {},
    data() {
        return {
            message: '',
        };
    },
    created() {},
    methods: {},
};
</script>

<style scoped>
</style>