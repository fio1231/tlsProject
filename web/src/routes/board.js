const board = [{
        path: "/board",
        name: "Board",
        component: () => import('/src/views/Board.vue'),
    },
    {
        path: "/board/detail",
        name: "BoardDetail",
        component: () => import('/src/views/BoardDetail.vue'),
    }
];

export default board;