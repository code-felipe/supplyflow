package com.custodia.supply.web.util;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.custodia.supply.customer.entity.CustomerSite;
import com.custodia.supply.request.entity.Request;
import com.custodia.supply.user.entity.User;

public class FlashGuardsRequest implements IFlashGuards<Request> {


	
	@Override
    public Optional<String> require(Request request, RedirectAttributes flash,
                                    String flashKey, String flashMsg, String redirectUrl) {
        if (request == null) {
            flash.addFlashAttribute(flashKey, flashMsg);
            return Optional.of(redirectUrl);
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> requireUserBound(Request request,
                                             RedirectAttributes flash,
                                             Function<Long, String> urlOnFail) {
        // Si user o su id no existen → usa SIEMPRE la URL provista
        if (request == null || request.getId() == null) {
            flash.addFlashAttribute("warning", "No user bound to the request.");
            Long id = (request != null ? request.getId() : null);
            return Optional.of(urlOnFail.apply(id)); // ← usa el callback del caller
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> requireActiveUser(Request request,
                                              RedirectAttributes flash,
                                              Function<Long, String> urlOnFail) {
        // 1) Primero asegura el bound (y define tu fallback cuando no hay id)
        Optional<String> r = requireUserBound(request, flash, id -> "redirect:/user/list");
        if (r.isPresent()) return r;

        // 2) Luego valida activo
        if (!Boolean.TRUE.equals(request.getUser().getIsActive())) {
            flash.addFlashAttribute("warning", "Sorry, the user is not active");
            return Optional.of(urlOnFail.apply(request.getId())); // aquí sí hay id
        }
        return Optional.empty();
    }
//	
//
//	public static Optional<String> requireUserBound(Request req, RedirectAttributes flash,
//			Function<Long, String> urlOnFail) {
//		if (req == null || req.getUser() == null || req.getUser().getId() == null) {
//			flash.addFlashAttribute("warning", "No user bound to the request.");
//			return Optional.of("redirect:/user/list");
//		}
//		return Optional.empty();
//	}
//
//	public static Optional<String> requireActiveUser(Request req, RedirectAttributes flash,
//			Function<Long, String> urlOnFail) {
//		if (req == null || req.getUser() == null || req.getUser().getId() == null) {
//			flash.addFlashAttribute("warning", "No user bound to the request.");
//			return Optional.of("redirect:/user/list");
//		}
//		User u = req.getUser();
//		if (!Boolean.TRUE.equals(u.getIsActive())) {
//			flash.addFlashAttribute("warning", "Sorry, the user is not active");
//			return Optional.of(urlOnFail.apply(u.getId()));
//		}
//		return Optional.empty();
//	}
//
//	public static Optional<String> requireAssignedSiteAndCustomer(Request req, RedirectAttributes flash,
//			Function<Long, String> urlOnFail) {
//		if (req == null || req.getUser() == null || req.getUser().getId() == null) {
//			flash.addFlashAttribute("warning", "No user bound to the request.");
//			return Optional.of("redirect:/user/list");
//		}
//		CustomerSite site = req.getUser().getAssignedSite();
//		if (site == null || site.getCustomer() == null) {
//			flash.addFlashAttribute("warning", "You must assign a customer to the request");
//			return Optional.of(urlOnFail.apply(req.getUser().getId()));
//		}
//		return Optional.empty();
//	}

}
