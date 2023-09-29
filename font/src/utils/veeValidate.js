/* eslint-disable camelcase */
import { extend } from "vee-validate";
import {
  required,
  email,
  alpha,
  numeric,
  alpha_spaces,
  alpha_dash,
  min_value,
} from "vee-validate/dist/rules";

const veeValidate = () => {
  // No message specified.
  extend("email", email);

  extend("alpha", alpha);

  extend("numeric", numeric);

  extend("alpha_spaces", alpha_spaces);

  extend("alpha_dash", alpha_dash);

  // Override the default message.
  extend("required", {
    ...required,
    message: "O campo é obrigatório",
  });

  extend("fotoObrigatoria", {
    ...required,
    message: "A foto é obrigatória",
  });

  extend("isEmpty", {
    validate(value, args) {
      const listSize = args[0];
      if (listSize > 0) {
        return true;
      }
      return false;
    },
    message: (field, args) => args[1],
  });

  extend("cpfCnpj", {
    validate(value) {
      return isValidCPF(value) || isValidCNPJ(value);
    },
    message: "CPF/CNPJ inválido",
    computesRequired: true,
  });

  extend("documentos", {
    validate(value) {
      return value && value.length > 0;
    },
    message: "Documentos obrigatórios",
    computesRequired: true,
  });

  extend("cnpj", {
    validate(value) {
      return isValidCNPJ(value);
    },
    message: "CNPJ inválido",
    computesRequired: true,
  });

  extend("cpf", {
    validate(value) {
      return isValidCPF(value);
    },
    message: "CPF inválido",
    computesRequired: true,
  });

  extend("cpfExists", () => {
    return "CPF já adicionado";
  });

  extend("cnpjExists", () => {
    return "CNPJ já adicionado";
  });

  extend("cpfCnpjExists", () => {
    return "CPF/CNPJ já está cadastrado!";
  });

  extend("cpfCnpjNaoEncontrado", () => {
    return "CPF/CNPJ não encontrado!";
  });

  extend("numeroInstrumentoExists", () => {
    return "Número de instrumento já está cadastrado!";
  });

  extend("telefone", {
    validate(value) {
      return isValidTelefone(value);
    },
    message: "O campo telefone deve ser um telefone válido",
    computesRequired: true,
  });

  extend("min_value", {
    ...min_value,
    message: (field, params) => {
      return "O valor do campo deve ser no mínimo " + params["min"] + ".";
    },
  });

  // Validação do CPF
  function isValidCPF(cpf) {
    if (cpf === undefined || cpf === null) return false;

    if ((cpf = cpf.replace(/[^\d]/g, "")).length !== 11) return false;

    const cpfInvalidos = [
      "00000000000",
      "11111111111",
      "22222222222",
      "33333333333",
      "44444444444",
      "55555555555",
      "66666666666",
      "77777777777",
      "88888888888",
      "99999999999",
    ];

    if (cpfInvalidos.indexOf(cpf) > -1) return false;

    let r, i;
    let s = 0;
    for (i = 1; i <= 9; i++) s += parseInt(cpf[i - 1]) * (11 - i);
    r = (s * 10) % 11;
    if (r === 10 || r === 11) r = 0;
    if (r !== parseInt(cpf[9])) return false;
    s = 0;
    for (i = 1; i <= 10; i++) s += parseInt(cpf[i - 1]) * (12 - i);
    r = (s * 10) % 11;

    if (r === 10 || r === 11) r = 0;

    if (r !== parseInt(cpf[10])) return false;

    return true;
  }

  function isValidCNPJ(cnpjFull) {
    if (cnpjFull === null || cnpjFull === undefined) {
      return false;
    }
    var cnpj = cnpjFull.toString().replace(/[-_./]/g, "");

    if (cnpj.length !== 14) {
      return false;
    }

    let i;
    const c = cnpj.substr(0, 12);
    const dv = cnpj.substr(12, 2);
    let d1 = 0;

    for (i = 0; i < 12; i++) {
      d1 += c.charAt(11 - i) * (2 + (i % 8));
    }

    if (d1 === 0) {
      return false;
    }

    d1 = 11 - (d1 % 11);

    if (d1 > 9) {
      d1 = 0;
    }

    if (parseInt(dv.charAt(0)) !== d1) {
      return false;
    }

    d1 *= 2;

    for (i = 0; i < 12; i++) {
      d1 += c.charAt(11 - i) * (2 + ((i + 1) % 8));
    }

    d1 = 11 - (d1 % 11);

    if (d1 > 9) {
      d1 = 0;
    }

    if (parseInt(dv.charAt(1)) !== d1) {
      return false;
    }

    return true;
  }
  function isValidTelefone(telefone) {
    if (telefone === "") return true;
    telefone = telefone.replace(/\D/g, "");
    if (!(telefone.length >= 10 && telefone.length <= 11)) return false;
    if (telefone.length === 11 && parseInt(telefone.substring(2, 3)) !== 9) {
      return false;
    }

    return true;
  }
};

export default veeValidate;
