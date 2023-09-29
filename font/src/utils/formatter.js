const moneyFormatter = (str) => {
  var formato = {
    minimumFractionDigits: 2,
    style: "currency",
    currency: "BRL",
  };
  return str.toLocaleString("pt-BR", formato);
};

const moneyFormatterWithoutRS = (str) => {
  var formato = { minimumFractionDigits: 2 };
  if (str) {
    return str.toLocaleString("pt-BR", formato);
  }
  return "-";
};

const formatDate = (date) => {
  if (!date) {
    return "-";
  }

  const dateConverted = new Date(date);
  return dateConverted.toLocaleDateString("pt-BR", {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
  });
};

const formatDateWithHours = (date) => {
  if (!date) {
    return "-";
  }

  const dateConverted = new Date(date);
  return dateConverted.toLocaleTimeString("pt-BR", {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
  });
};

const stringToDate = (str) => {
  const dataDividida = str.split("/");
  const dataFormatadaFinal = new Date(
    parseInt(dataDividida[2]),
    parseInt(dataDividida[1]) - 1,
    parseInt(dataDividida[0])
  );
  return dataFormatadaFinal;
};

const slugify = (str) => {
  var map = {
    a: "á|à|ã|â|À|Á|Ã|Â",
    e: "é|è|ê|É|È|Ê",
    i: "í|ì|î|Í|Ì|Î",
    o: "ó|ò|ô|õ|Ó|Ò|Ô|Õ",
    u: "ú|ù|û|ü|Ú|Ù|Û|Ü",
    c: "ç|Ç",
    n: "ñ|Ñ",
  };

  for (var pattern in map) {
    str = str.replace(new RegExp(map[pattern], "g"), pattern);
  }

  return str;
};

const formatTagsToString = (array) => {
  let str = "";
  for (const i in array) {
    str += Number(i) === 0 ? array[i] : `, ${array[i]}`;
  }
  return str;
};

const formatTagsToArrray = (tags) => {
  const tagsArray = tags.split(",");
  const formatedTags = tagsArray.map((tag) => tag.trim());
  return formatedTags;
};

export default {
  moneyFormatter,
  moneyFormatterWithoutRS,
  formatDate,
  slugify,
  stringToDate,
  formatDateWithHours,
  formatTagsToString,
  formatTagsToArrray,
};
