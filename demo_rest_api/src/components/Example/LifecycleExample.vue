<template>
    <div>
        <p>Message: {{ message }}</p>
        <p>Counter: {{ counter }}</p>
        <button @click="incrementCounter">Increment Counter</button>
    </div>
</template>

<script>
export default {
    data() {
        return {
            message: 'Hello',
            counter: 0
        };
    },
    beforeCreate() {
        console.log('beforeCreate');
    },
    created() {
        console.log('created');
    },
    beforeMount() {
        console.log('beforeMount');
    },
    mounted() {
        console.log('mounted');
    },
    beforeUpdate() {
        console.log('beforeUpdate');
    },
    updated() {
        console.log('updated');
    },
    beforeUnmount() {
        console.log('beforeUnmount');
    },
    unmounted() {
        console.log('unmounted');
    },
    methods: {
        incrementCounter() {
            this.counter++;
        }
    }
};
</script>
