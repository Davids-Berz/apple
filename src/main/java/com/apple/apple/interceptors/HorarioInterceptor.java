package com.apple.apple.interceptors;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Qualifier("horario")
public class HorarioInterceptor implements HandlerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(HorarioInterceptor.class);

    @Value("${config.horario.in}")
    private Integer apertura;

    @Value("${config.horario.out}")
    private Integer cierre;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        LOG.info("Interceptando: " + handler);
        Calendar calendar = Calendar.getInstance();
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        LOG.info("horario actual: " + hora);
        if (hora >= apertura && hora < cierre) {
            StringBuilder info = new StringBuilder();
            info.append("Bienvenido")
                    .append(" atendemos desde las ")
                    .append(apertura).append(" hrs. ")
                    .append(cierre).append(" hrs. ");
            request.setAttribute("info", info.toString());
            return true;
        }
        response.sendRedirect(request.getContextPath().concat("/cerrado"));
        return false;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable ModelAndView modelAndView) throws Exception {
        String info = (String) request.getAttribute("info");
        if (modelAndView != null && handler instanceof HandlerMethod)
            modelAndView.addObject("info", info);
    }

}
