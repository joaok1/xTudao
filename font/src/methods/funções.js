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
      `http://localhost:1080/api/usuarios/findByLogin/${Cookies.get(
        "dados_usuario"
      )}`
    );
    return (await usuario).data.id;
  },

  async Login() {
    const usuario = axios.get(
      `http://localhost:1080/api/usuarios/findByLogin/${Cookies.get(
        "dados_usuario"
      )}`
    );
    return usuario;
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
  async listAllFuncionarioPage(page) {
    const data = {
      method: "get",
      url: `http://localhost:1080/api/pessoa/findByAllPessoa?size=10&page=${page}`,
      headers: {
        Authorization: `Bearer ${Cookies.get("token")}`,
      },
    };
    return axios(data);
  },
  async cadastroFuncionario(object) {
    const data = {
      method: "post",
      url: `http://localhost:1080/api/pessoa/adicionar`,
      data: object,
      headers: {
        Authorization: `Bearer ${Cookies.get("token")}`,
      },
    };
    return axios(data);
  },
  async excluirFuncionario(id) {
    const data = {
      method: "delete",
      url: `http://localhost:1080/api/usuarios/deleteById/${id}`,
      headers: {
        Authorization: `Bearer ${Cookies.get("token")}`,
      },
    };
    return axios(data);
  },
  async findByIdPessoa(id) {
    const data = {
      method: "get",
      url: `http://localhost:1080/api/pessoa/findById/${id}`,
      headers: {
        Authorization: `Bearer ${Cookies.get("token")}`,
      },
    };
    return axios(data);
  },
  async editarFuncionario(object) {
    const data = {
      method: "put",
      url: `http://localhost:1080/api/pessoa/editar`,
      data: object,
      headers: {
        Authorization: `Bearer ${Cookies.get("token")}`,
      },
    };
    return axios(data);
  },
};
export default actions;
