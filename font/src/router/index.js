import Vue from "vue";
import VueRouter from "vue-router";
import home from "../views/home/home.vue";
import login from "../views/Login.vue";
import listProdutos from "../views/produtos/ListProdutos.vue";
import listFuncionario from "@/views/funcionarios/ListFuncionarios.vue";
import cadastrarFuncionario from "@/views/funcionarios/CadastrarFuncionario.vue";
import visualizarFuncionario from "@/views/funcionarios/Visualizar.vue";
import editarFuncionario from "@/views/funcionarios/EditarFuncionario.vue";

import store from "@/store";

Vue.use(VueRouter);

const requireAuth = (to, from, next) => {
  if (!store.state.authenticated) {
    next("/login");
  } else {
    next();
  }
};

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes: [
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
      path: "/funcionarios",
      name: "listFuncionarios",
      component: listFuncionario,
      beforeEnter: requireAuth,
    },
    {
      name: "cadastroFuncionarios",
      path: "/funcionarios/cadastrar",
      component: cadastrarFuncionario,
      beforeEnter: requireAuth,
    },
    {
      name: "visualizarFuncionario",
      path: "/funcionarios/visualizar",
      component: visualizarFuncionario,
      beforeEnter: requireAuth,
    },
    {
      name: "editarFuncionario",
      path: "/funcionarios/editar",
      component: editarFuncionario,
      beforeEnter: requireAuth,
    },
  ],
});

export default router;
