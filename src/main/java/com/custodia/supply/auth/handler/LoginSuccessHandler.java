package com.custodia.supply.auth.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import com.custodia.supply.user.entity.User;
import com.custodia.supply.user.service.IUserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	
	@Autowired
	private IUserService userService;
	
//	public LoginSuccessHandler() {
//		
//		 // Asegura un único redirect al destino final
//        setAlwaysUseDefaultTargetUrl(true);
//        setDefaultTargetUrl("/user/list");
//	}
//	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		String redirectURL = request.getContextPath();

        var authorities = authentication.getAuthorities();
        
        boolean isAdmin = authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isSupervisor = authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_SUPERVISOR"));
        boolean isConcierge = authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_CONCIERGE"));
        
        if (isAdmin) {
            redirectURL = "/user/list";
        } else if (isSupervisor) {
            redirectURL = "/user/list"; // solo vistas, controlado con reglas abajo
        } else if (isConcierge) {
            String email = authentication.getName(); // es el email (username)
            User u = userService.findByEmail(email);
            if(u != null && u.getIsActive() != null && u.getIsActive()) {
            	redirectURL = "/user/view/" + u.getId();
            }
            
        } else {
            // fallback razonable
            redirectURL = "/user/list";
        }
       
		
		
		response.sendRedirect(redirectURL);
//		super.onAuthenticationSuccess(request, response, authentication);
	}
	
	
	private Long getUserIdByEmail(String email) {
        // aquí consultas en tu UserService para obtener el id
        return userService.findByEmail(email).getId(); // ejemplo
    }

}
/* otra implmentacion para tratar el id
 *  private final IUserService userService;

    public LoginSuccessHandler(IUserService userService) {
        this.userService = userService;
        // Si quieres respetar SavedRequest (p.ej. ADMIN que estaba yendo a /algo)
        setAlwaysUseDefaultTargetUrl(false);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        String target = decideTarget(authentication);
        // Usa RedirectStrategy de Spring (opcional, pero prolijo)
        getRedirectStrategy().sendRedirect(request, response, target);
    }

    private String decideTarget(Authentication auth) {
        var authorities = auth.getAuthorities();

        boolean isAdmin      = authorities.stream().anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
        boolean isSupervisor = authorities.stream().anyMatch(a -> "ROLE_SUPERVISOR".equals(a.getAuthority()));
        boolean isConcierge  = authorities.stream().anyMatch(a -> "ROLE_CONCIERGE".equals(a.getAuthority()));

        if (isAdmin || isSupervisor) {
            return "/user/list";
        }

        if (isConcierge) {
            String email = auth.getName(); // tu username = email
            var u = userService.findByEmail(email);
            if (u != null && u.getIsActive() != null && u.getIsActive()) {
                return "/user/view/" + u.getId(); // propio perfil
            }
            // fallback si no estuviera activo o no encontrado
            return "/login?inactive";
        }

        // fallback para otros roles
        return "/user/list";
    }
 * 
 */
/*
 * AI Fix, because the flash message is not showing up, after the success login.
 * @Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  public LoginSuccessHandler() {
    setDefaultTargetUrl("/user/list");        // destino por defecto
    setAlwaysUseDefaultTargetUrl(true);       // opcional: siempre /user/list
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
                                      HttpServletResponse response,
                                      Authentication auth)
      throws IOException, ServletException {

    var flash = new org.springframework.web.servlet.FlashMap();
    flash.put("success", "Welcome, " + auth.getName() + "!");

    // ata el flash al path real de la próxima request
    flash.setTargetRequestPath("/user/list");

    new org.springframework.web.servlet.support.SessionFlashMapManager()
        .saveOutputFlashMap(flash, request, response);

    super.onAuthenticationSuccess(request, response, auth);
  }
}
======SpringSecurityConfig add this:
.formLogin(login -> login
  .loginPage("/login")
  .successHandler(successHandler)   // ← solo esto
  .permitAll()
)


 */
