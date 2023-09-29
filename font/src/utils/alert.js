import vue from "@/main";
import messages from "@/constants/messages";

export default {
  error({
    msg,
    timeout = 5000,
    position = "toast-top-right",
    title = messages.TITULO_ERROR,
  }) {
    vue.$toastr(messages.TIPO_ERROR, {
      title,
      msg,
      clickClose: true,
      timeout,
      position,
    });
  },
  warning({
    msg,
    timeout = 5000,
    position = "toast-top-right",
    title = vue.$messages.TITULO_WARNING,
  }) {
    vue.$toastr(messages.TIPO_WARNING, {
      title,
      msg,
      clickClose: true,
      timeout,
      position,
    });
  },
};
