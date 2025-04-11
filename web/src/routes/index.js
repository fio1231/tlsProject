import {createRouter, createWebHistory} from "vue-router";
import board from "./board.js";

const router = createRouter({
    history: createWebHistory(),
    routes:[...board],
});

export default router;