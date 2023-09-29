import Vue from "vue";
import Vuex from "vuex";
import VueRouter from "vue-router";
import axios from "axios";
import Cookies from "js-cookie";
Vue.use(VueRouter);
Vue.use(Vuex);

const actions = {
  //Configurações globais do usuario
  //Resgata o usuario logado

  // Busca o id do usuario
  async dadosLogin() {
    const usuario = axios.get(
      `http://localhost:1081/api/usuarios/findByLogin/${Cookies.get(
        "dados_usuario"
      )}`
    );
    return (await usuario).data.id;
  },

  async Login() {
    const usuario = axios.get(
      `http://localhost:1081/api/usuarios/findByLogin/${Cookies.get(
        "dados_usuario"
      )}`
    );
    return await usuario;
  },
  async getListagemProdutos(page) {
    const data = {
      method: "get",
      url: `http://localhost:1080/api/ingrediente/findAll?size=10&page=${page}`,
      headers: {
        Authorization: `Bearer ${Cookies.get("token")}`,
      },
    };
    return axios(data);
  },
};
export default actions;
