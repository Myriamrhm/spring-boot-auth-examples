package net.secudev.auth.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class TracableAspect {
	@Around("@annotation(Tracable)")
	public Object handle(ProceedingJoinPoint joinPoint) throws Throwable {

		
		///AMELIORATION :
		//Tisser autour de la classe annotee pour injecter le code de log dans toutes
		//les methodes
		
		
		// Il s'agit d'intercepter ce que fait une méthode annotée à l'éxécution et
		// logger

		Object obj = joinPoint.getThis();// Capture de la chose interceptée ;)
		
		

		// Ici je bosses avec les contextes de spring, mais on peut aussi avoir besoin
		// d'utiliser la reflection pour connaitre la méthode interceptée et ses
		// arguments etc
		// Method method = ((MethodSignature) joinPoint.getSignature()).getMethod(); //
		// Récupération par cast de la méthode interceptée

		Logger logger = LoggerFactory.getLogger(obj.getClass());

		String ip = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest()
				.getRemoteAddr();
		String url = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest()
				.getRequestURI();

		logger.trace(SecurityContextHolder.getContext().getAuthentication().getName() + " a appellé " + url + " depuis "
				+ ip);

		// Il retourner le joinpoint Sinon la methode intercepté ne fait plus rien, donc
		// on la renvoi, ou sous une
		// autre forme
		// return target.invoke(obj, joinPoint.getArgs()); // permet d'attrapper les
		// parmètres voire les transformer si besoin
		return joinPoint.proceed();
	}
}