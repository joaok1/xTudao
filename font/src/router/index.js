import Vue from "vue";
import VueRouter from "vue-router";
import home from "../views/home/home.vue";
import login from "../views/Login.vue";
import listProdutos from "../views/produtos/ListProdutos.vue";
import listFuncionario from "../views/funcionarios/ListFuncionarios.vue";
import cadastrarFuncionario from "../views/funcionarios/CadastrarFuncionario.vue";

import store from "@/store";

Vue.use(VueRouter);

const requireAuth = (to, from, next) => {
  if (!store.state.authenticated) {
    next("/login");
  } else {
    next();
  }
};

const routes = [
  {
    path: "/",
    name: "home",
    component: home,
    beforeEnter: requireAuth,
  },
  {
    path: "/login",
    name: "Login",
    component: login,
  },
  {
    path: "/listagem/produtos",
    name: "listagemprodutos",
    component: listProdutos,
    beforeEnter: requireAuth,
  },
  {
    path: "/listagem/funcionarios",
    name: "listagemFuncionarios",
    component: listFuncionario,
    beforeEnter: requireAuth,
  },
  {
    path: "/cadastrar/funcionarios",
    name: "cadastrarFuncionarios",
    component: cadastrarFuncionario,
    beforeEnter: requireAuth,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
