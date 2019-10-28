package github.hoanv810.icon.util.scheduler

import javax.inject.Qualifier

/**
 * @author hoanv
 * @since 7/17/17.
 *
 * Qualifier to define scheduler type (io, computation or UI main thread).
 */
@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class RunOn(val value: SchedulerType = SchedulerType.IO)