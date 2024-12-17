<template>
    <div>
        <h1 class="underline">{{title}}</h1>
        <router-view class="content px-3 rounded" @getTitle="getTitle"></router-view>
    </div>
</template>

<script>
export default {
    name: 'UserView',
    components: {},
    data() {
        return {
            message: '',
            title:""
        };
    },
    created() {},
    methods: {
        getTitle(title){
            this.title = title;
        }
    },
};
</script>

<style scoped></style>