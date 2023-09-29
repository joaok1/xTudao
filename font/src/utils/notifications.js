import { Notification } from "element-ui";

export default {
  success: (title, message) => {
    if (title === undefined || message === undefined) {
      throw new Error("Forneça um título e uma mensagem para a notificação!");
    }
    Notification.success({
      title,
      message,
    });
  },

  error: (title = "Erro!", message = "Tente novamente.") => {
    Notification.error({
      title,
      message,
    });
  },

  info: (title, message) => {
    if (title === undefined || message === undefined) {
      throw new Error("Forneça um título e uma mensagem para a notificação!");
    }
    Notification.info({
      title,
      message,
    });
  },
};
