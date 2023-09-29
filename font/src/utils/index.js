import configs from "@/constants/configs";

const isJson = (str) => {
  try {
    JSON.parse(str);
  } catch (e) {
    return false;
  }
  return true;
};

const getDate = (str) => {
  if (!str) {
    return;
  }

  return str.split(" ")[0];
};

const format = (
  input,
  opt = { prefix: "", suffix: "", thousands: ".", decimal: ",", precision: 2 }
) => {
  if (!input) return "0";

  if (typeof input === "number") {
    input = input.toFixed(fixed(opt.precision));
  }
  const negative = input.indexOf("-") >= 0 ? "-" : "";

  const numbers = onlyNumbers(input);
  const currency = numbersToCurrency(numbers, opt.precision);
  const parts = toStr(currency).split(".");
  let integer = parts[0];
  const decimal = parts[1];
  integer = addThousandSeparator(integer, opt.thousands);
  return (
    opt.prefix +
    negative +
    joinIntegerAndDecimal(integer, decimal, opt.decimal) +
    opt.suffix
  );
};
const isPDF = (nomeArquivo) => {
  return nomeArquivo.split(".").pop() === "pdf";
};

function toStr(value) {
  return value ? value.toString() : "";
}

function onlyNumbers(input) {
  return toStr(input).replace(/\D+/g, "") || "0";
}

// Uncaught RangeError: toFixed() digits argument must be between 0 and 20 at Number.toFixed
function between(min, n, max) {
  return Math.max(min, Math.min(n, max));
}

function fixed(precision) {
  return between(0, precision, 20);
}

const numbersToCurrency = (numbers, precision) => {
  const exp = 10 ** precision;
  const float = parseFloat(numbers) / exp;
  return float.toFixed(fixed(precision));
};

function addThousandSeparator(integer, separator) {
  return integer.replace(/(\d)(?=(?:\d{3})+\b)/gm, `$1${separator}`);
}

function joinIntegerAndDecimal(integer, decimal, separator) {
  return decimal ? integer + separator + decimal : integer;
}

const unformat = (input, precision) => {
  const negative = input.indexOf("-") >= 0 ? -1 : 1;
  const numbers = onlyNumbers(input);
  const currency = numbersToCurrency(numbers, precision);
  return parseFloat(currency) * negative;
};

function calculaDiasUteis(dataInicio, dataFim) {
  let dataDividia = dataFim.split("/");

  const dataF = new Date(
    parseInt(dataDividia[2]),
    parseInt(dataDividia[1]) - 1,
    parseInt(dataDividia[0])
  );
  dataDividia = dataInicio.split("/");
  const dataI = new Date(
    parseInt(dataDividia[2]),
    parseInt(dataDividia[1]) - 1,
    parseInt(dataDividia[0])
  );

  let diasUteis = 0;
  let hoje = dataI.getDay();
  const timeDiff = Math.abs(dataF.getTime() - dataI.getTime());
  const diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));
  for (let index = 0; index < diffDays + 1; index++) {
    hoje = dataI.getDay();
    if (hoje !== 0 && hoje !== 6) {
      diasUteis++;
    }
    dataI.setDate(dataI.getDate() + 1);
  }
  return diasUteis;
}

const openFileOnWindow = (file) => {
  const newBlob = new Blob([file], { type: "application/pdf" });
  const data = window.URL.createObjectURL(newBlob);
  const link = document.createElement("a");
  link.href = data;
  window.open(data);
  // eslint-disable-next-line space-before-function-paren
  setTimeout(function () {
    // For Firefox it is necessary to delay revoking the ObjectURL
    document.body.removeChild(link);
    window.URL.revokeObjectURL(data);
  }, 100);
};

const toCapitalize = (string) => {
  string = string.toLowerCase();
  return string.charAt(0).toUpperCase() + string.slice(1);
};

const formataValorEmReais = (value) => {
  return Number(value).toLocaleString("pt-br", {
    style: "currency",
    currency: "BRL",
  });
};

const goToSelecaoModulos = () => {
  window.location.href =
    "http://" +
    (process.env.NODE_ENV === "development"
      ? window.location.hostname + ":8081"
      : window.location.hostname) +
    "/#/selecionarModulo";
};

const getAcaoCadastrarItem = () => {
  return configs.ACOES.CADASTRAR_BEM;
};

const getRouteGroupItem = () => {
  return configs.ROUTE_GROUPS.BENS_PATRIMONIO;
};

const formatPriceToNumber = (value) => {
  return Number(
    value.toString().replace("R$", "").replaceAll(".", "").replaceAll(",", ".")
  );
};

export default {
  isJson,
  getDate,
  format,
  unformat,
  calculaDiasUteis,
  isPDF,
  openFileOnWindow,
  toCapitalize,
  formataValorEmReais,
  goToSelecaoModulos,
  getAcaoCadastrarItem,
  getRouteGroupItem,
  formatPriceToNumber,
};
