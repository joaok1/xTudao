import configs from "@/constants/configs";

import abreFechaCompetencia from "@/assets/abreFechaCompetencia.png";
import movimentacao from "@/assets/movimentacao.png";
import registroBaixas from "@/assets/registroBaixas.png";
import registroEntradas from "@/assets/registroEntradas.png";
import validacaoDeBens from "@/assets/validacaoDeBens.png";
import transferencia from "@/assets/transferencia.png";
import manutencao from "@/assets/manutencao.png";
import cadastros from "@/assets/cadastros.png";
import voltar from "@/assets/voltar.png";
import depreciacao from "@/assets/depreciacao.png";
import reavaliacao from "@/assets/reavaliacao.png";
import registroInventario from "@/assets/registroInventario.png";
import termoResponsabilidade from "@/assets/termoResponsabilidade.png";
import dashboard from "@/assets/dashboard.png";
import marcasMaterial from "@/assets/marcasMaterial.png";
import material from "@/assets/material.png";
import marca from "@/assets/marca.png";

export default [
  {
    isItemMenu: true,
    nome: "Abre/Fecha competência",
    icon: abreFechaCompetencia,
    routeName: "abreFechaCompetencia",
    routeGroup: configs.ROUTE_GROUPS.BENS_PATRIMONIO,
    permissaoTela: configs.ACOES.LISTAR_BEM,
  },
  {
    isItemMenu: true,
    nome: "Movimentação",
    icon: movimentacao,
    routeName: "",
    routeGroup: configs.ROUTE_GROUPS.BENS_PATRIMONIO,
    permissaoTela: configs.ACOES.LISTAR_BEM,
  },
  {
    isItemMenu: true,
    nome: "Registro de inventário",
    icon: registroInventario,
    routeName: "inventario",
    routeGroup: configs.ROUTE_GROUPS.BENS_PATRIMONIO,
    permissaoTela: configs.ACOES.LISTAR_BEM,
  },
  {
    isItemMenu: true,
    nome: "Termo de responsabilidade",
    icon: termoResponsabilidade,
    routeName: "termoResponsabilidade",
    routeGroup: configs.ROUTE_GROUPS.BENS_PATRIMONIO,
    permissaoTela: configs.ACOES.LISTAR_BEM,
  },
  {
    isItemMenu: true,
    nome: "Dashboard",
    icon: dashboard,
    routeName: "dashboard",
    routeGroup: configs.ROUTE_GROUPS.BENS_PATRIMONIO,
    permissaoTela: configs.ACOES.LISTAR_BEM,
  },
  {
    isItemMenu: true,
    nome: "Gestão de bens",
    icon: cadastros,
    routeName: "gestao",
    routeGroup: configs.ROUTE_GROUPS.BENS_PATRIMONIO,
    permissaoTela: configs.ACOES.LISTAR_BEM,
  },
  {
    isItemMenu: true,
    nome: "Cadastros",
    icon: marcasMaterial,
    routeName: "",
    routeGroup: configs.ROUTE_GROUPS.BENS_PATRIMONIO,
    permissaoTela: configs.ACOES.LISTAR_BEM,
  },
  // Submenus Cadastros
  {
    isCadastrosSubMenu: true,
    nome: "Materiais",
    icon: material,
    routeName: "listagemTipoMaterial",
    routeGroup: configs.ROUTE_GROUPS.BENS_PATRIMONIO,
    permissaoTela: configs.ACOES.LISTAR_BEM,
  },
  {
    isCadastrosSubMenu: true,
    nome: "Marca",
    icon: marca,
    routeName: "listagemMarca",
    routeGroup: configs.ROUTE_GROUPS.BENS_PATRIMONIO,
    permissaoTela: configs.ACOES.LISTAR_BEM,
  },
  // Submenus Movimentação
  {
    isItemSubMenu: true,
    nome: "Registro de entradas",
    icon: registroEntradas,
    routeName: "listagemRegistroEntrada",
    routeGroup: configs.ROUTE_GROUPS.BENS_PATRIMONIO,
    permissaoTela: configs.ACOES.LISTAR_BEM,
  },
  {
    isItemSubMenu: true,
    nome: "Validação de bens",
    icon: validacaoDeBens,
    routeName: "listagemValidacaoBens",
    routeGroup: configs.ROUTE_GROUPS.BENS_PATRIMONIO,
    permissaoTela: configs.ACOES.LISTAR_BEM,
  },
  {
    isItemSubMenu: true,
    nome: "Registro de baixas",
    icon: registroBaixas,
    routeName: "listagemRegistroBaixa",
    routeGroup: configs.ROUTE_GROUPS.BENS_PATRIMONIO,
    permissaoTela: configs.ACOES.LISTAR_BEM,
  },
  {
    isItemSubMenu: true,
    nome: "Transferência",
    icon: transferencia,
    routeName: "listagemTransferencia",
    routeGroup: configs.ROUTE_GROUPS.BENS_PATRIMONIO,
    permissaoTela: configs.ACOES.LISTAR_BEM,
  },
  {
    isItemSubMenu: true,
    nome: "Manutenção",
    icon: manutencao,
    routeName: "listagemManutencao",
    routeGroup: configs.ROUTE_GROUPS.BENS_PATRIMONIO,
    permissaoTela: configs.ACOES.LISTAR_BEM,
  },
  {
    isItemSubMenu: true,
    nome: "Depreciação",
    icon: depreciacao,
    routeName: "listagemDepreciacao",
    routeGroup: configs.ROUTE_GROUPS.BENS_PATRIMONIO,
    permissaoTela: configs.ACOES.LISTAR_BEM,
  },
  {
    isItemSubMenu: true,
    nome: "Reavaliação",
    icon: reavaliacao,
    routeName: "listagemReavaliacao",
    routeGroup: configs.ROUTE_GROUPS.BENS_PATRIMONIO,
    permissaoTela: configs.ACOES.LISTAR_BEM,
  },
  // Submenus Empréstimo
  {
    isItemSubMenu: true,
    nome: "Empréstimo",
    icon: cadastros,
    routeName: "",
    routeGroup: configs.ROUTE_GROUPS.BENS_PATRIMONIO,
    permissaoTela: configs.ACOES.LISTAR_BEM,
  },
  {
    isEmprestimoSubMenu: true,
    nome: "Reservas de bens",
    icon: depreciacao,
    routeName: "reserva",
    routeGroup: configs.ROUTE_GROUPS.BENS_PATRIMONIO,
    permissaoTela: configs.ACOES.LISTAR_BEM,
  },
  {
    isEmprestimoSubMenu: true,
    nome: "Registro de empréstimo interno",
    icon: registroEntradas,
    routeName: "listagemEmprestimoInterno",
    routeGroup: configs.ROUTE_GROUPS.BENS_PATRIMONIO,
    permissaoTela: configs.ACOES.LISTAR_BEM,
  },
  {
    isEmprestimoSubMenu: true,
    nome: "Registro de empréstimo a terceiros",
    icon: registroEntradas,
    routeName: "listagemEmprestimoExterno",
    routeGroup: configs.ROUTE_GROUPS.BENS_PATRIMONIO,
    permissaoTela: configs.ACOES.LISTAR_BEM,
  },
  // Favor manter o objeto abaixo sempre no final deste arquivo
  {
    isItemSubMenu: true,
    nome: "Voltar",
    icon: voltar,
    routeName: "",
    routeGroup: configs.ROUTE_GROUPS.BENS_PATRIMONIO,
    permissaoTela: configs.ACOES.LISTAR_BEM,
  },
  {
    isEmprestimoSubMenu: true,
    nome: "Voltar",
    icon: voltar,
    routeName: "",
    routeGroup: configs.ROUTE_GROUPS.BENS_PATRIMONIO,
    permissaoTela: configs.ACOES.LISTAR_BEM,
  },
  {
    isCadastrosSubMenu: true,
    nome: "Voltar",
    icon: voltar,
    routeName: "",
    routeGroup: configs.ROUTE_GROUPS.BENS_PATRIMONIO,
    permissaoTela: configs.ACOES.LISTAR_BEM,
  },
];
