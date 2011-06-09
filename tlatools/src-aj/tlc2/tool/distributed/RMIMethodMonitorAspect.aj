package tlc2.tool.distributed;

public aspect RMIMethodMonitorAspect {
	
	// catch all method calls to RMI methods
	pointcut callToRemoteMethod() : 
		   execution(* tlc2.tool.distributed.InternRMI.*(..))
		|| execution(* tlc2.tool.distributed.TLCServerRMI.*(..))
		|| execution(* tlc2.tool.distributed.TLCWorkerRMI.*(..))
		|| execution(* tlc2.tool.distributed.FPSetRMI.*(..))
		|| execution(* tlc2.tool.distributed.FPIntSetRMI.*(..));
	
	before(): (callToRemoteMethod()) {
		RMIMethodMonitor.entering(thisJoinPoint);
	}
}
