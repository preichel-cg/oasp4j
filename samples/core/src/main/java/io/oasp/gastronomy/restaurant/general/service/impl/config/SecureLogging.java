package io.oasp.gastronomy.restaurant.general.service.impl.config;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * @author preichel
 *
 */
/**
 * @author preichel
 *
 */
public class SecureLogging {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(SecureLogging.class);

  private static String extClass = "org.owasp.security.logging.SecurityMarkers";

  private static String methodName = "getMarker";

  private static String methodParam = "";

  private static boolean initialized = false;

  private static Marker markerSecSuccConf = null;

  private static Marker markerSecFailConf = null;

  private static Marker markerSecAuditConf = null;

  private static final String CONFIDENTIAL_MARKER_NAME = "CONFIDENTIAL";

  private static final String SECURITY_SUCCESS_MARKER_NAME = "SECURITY SUCCESS";

  private static final String SECURITY_FAILURE_MARKER_NAME = "SECURITY FAILURE";

  private static final String SECURITY_AUDIT_MARKER_NAME = "SECURITY AUDIT";

  private static final String SECURITY_SUCCESS_CONFIDENTIAL_MARKER_NAME = "SECURITY SUCCESS CONFIDENTIAL";

  private static final String SECURITY_FAILURE_CONFIDENTIAL_MARKER_NAME = "SECURITY FAILURE CONFIDENTIAL";

  private static final String SECURITY_AUDIT_CONFIDENTIAL_MARKER_NAME = "SECURITY AUDIT CONFIDENTIAL";

  /**
   * Marker for Confidential log events.
   */
  public static final Marker CONF = MarkerFactory.getDetachedMarker(CONFIDENTIAL_MARKER_NAME);

  /**
   * Marker for Security Success log events.
   */
  public static final Marker SECUR_SUCC = MarkerFactory.getDetachedMarker(SECURITY_SUCCESS_MARKER_NAME);

  /**
   * Marker for Security Failure log events.
   */
  public static final Marker SECUR_FAIL = MarkerFactory.getDetachedMarker(SECURITY_FAILURE_MARKER_NAME);

  /**
   * Marker or MultiMarker for Confidential Security Success log events.
   */
  public static final Marker SECUR_SUCC_CONF = getMarkerSecSuccConf();

  /**
   * Marker or MultiMarker for Confidential Security Failure log events.
   */
  public static final Marker SECUR_FAIL_CONF = getMarkerSecFailConf();

  /**
   * Marker or MultiMarker for Confidential Security Audit log events.
   */
  public static final Marker SECUR_AUDIT_CONF = getMarkerSecAuditConf();

  private SecureLogging() {
  }

  private static Marker getMarkerSecSuccConf() {

    initMarkers();
    return markerSecSuccConf;
  }

  private static Marker getMarkerSecFailConf() {

    initMarkers();
    return markerSecFailConf;
  }

  private static Marker getMarkerSecAuditConf() {

    initMarkers();
    return markerSecAuditConf;
  }

  /**
   * Main method to initialize the {@link Marker}s provided by this class.
   */
  private static void initMarkers() {

    if (initialized)
      return;

    Class<?> cExtClass = hasExtClass(extClass);

    if (cExtClass.isAssignableFrom(String.class)) {
      createDefaultMarkers();
    } else {
      createMultiMarkers(cExtClass);
    }

    if (!initialized)
      LOG.warn("SecureLogging Markers could not be initialized!");
    else
      LOG.debug("SecureLogging Markers created: '{}', ...", markerSecSuccConf.getName());
    return;
  }

  private static void createDefaultMarkers() {

    LOG.debug("Creating default markers.");
    markerSecSuccConf = MarkerFactory.getDetachedMarker(SECURITY_SUCCESS_CONFIDENTIAL_MARKER_NAME);
    markerSecFailConf = MarkerFactory.getDetachedMarker(SECURITY_FAILURE_CONFIDENTIAL_MARKER_NAME);
    markerSecAuditConf = MarkerFactory.getDetachedMarker(SECURITY_AUDIT_CONFIDENTIAL_MARKER_NAME);
    initialized = true;
  }

  private static void createMultiMarkers(Class<?> cExtClass) {

    LOG.debug("Creating MultiMarkers.");

    Object objExtClass = null;
    try {
      objExtClass = cExtClass.newInstance();
      Class<?>[] paramTypes = { Marker[].class }; // the method to invoke is "getMarker(Marker... markers)".
      Method m = cExtClass.getMethod(methodName, paramTypes);

      Marker[] combineMarkers = { MarkerFactory.getDetachedMarker(SECURITY_SUCCESS_MARKER_NAME),
      MarkerFactory.getDetachedMarker(CONFIDENTIAL_MARKER_NAME) };
      markerSecSuccConf = (Marker) m.invoke(objExtClass, (Object) combineMarkers);
      combineMarkers[0] = MarkerFactory.getDetachedMarker(SECURITY_FAILURE_MARKER_NAME);
      markerSecFailConf = (Marker) m.invoke(objExtClass, (Object) combineMarkers);
      combineMarkers[0] = MarkerFactory.getDetachedMarker(SECURITY_AUDIT_MARKER_NAME);
      markerSecAuditConf = (Marker) m.invoke(objExtClass, (Object) combineMarkers);
      initialized = true;

    } catch (Exception e) {
      LOG.warn("Error getting Method '{}' of Class '{}'. Falling back to default.", methodName, cExtClass.getName());
      LOG.warn("Exception occurred.", e);
      e.printStackTrace();
      createDefaultMarkers();
    }
  }

  // private static void createMultiMarkers(Class cExtClass) {
  //
  // LOG.debug("Creating MultiMarkers.");
  // Object objExtClass = null;
  // try {
  // objExtClass = cExtClass.newInstance();
  // Method[] allMethods = cExtClass.getDeclaredMethods();
  // for (Method m : allMethods) {
  // String mname = m.getName();
  // if (mname.equalsIgnoreCase(methodName)) {
  // LOG.debug("Found Method '{}', execute now...", mname);
  // Marker[] combineMarkers = { MarkerFactory.getDetachedMarker(SECURITY_SUCCESS_MARKER_NAME),
  // MarkerFactory.getDetachedMarker(CONFIDENTIAL_MARKER_NAME) };
  // markerSecSuccConf = (Marker) m.invoke(objExtClass, (Object) combineMarkers);
  // combineMarkers[0] = MarkerFactory.getDetachedMarker(SECURITY_FAILURE_MARKER_NAME);
  // markerSecFailConf = (Marker) m.invoke(objExtClass, (Object) combineMarkers);
  // combineMarkers[0] = MarkerFactory.getDetachedMarker(SECURITY_AUDIT_MARKER_NAME);
  // markerSecAuditConf = (Marker) m.invoke(objExtClass, (Object) combineMarkers);
  // initialized = true;
  // }
  // }
  // } catch (Exception e) {
  // LOG.warn("Error getting Method '{}' of Class '{}'. Falling back to default.", methodName, cExtClass.getName());
  // LOG.warn("Exception occurred.", e);
  // e.printStackTrace();
  // createDefaultMarkers();
  // }
  // }

  /**
   * @return The given {@link Class} if parameter 'className' can be resolved, otherwise {@link String}.class.
   */
  private static Class<?> hasExtClass(String className) {

    Class<?> cExtClass;
    try {
      cExtClass = Class.forName(className);
      return cExtClass;
    } catch (Exception e) {
      LOG.debug("Class '{}' or one of its dependencies is not present.", className);
      // LOG.debug("Exception occured.", e);
      cExtClass = String.class;
      return cExtClass;
    }
  }

  /**
   * @return extClass
   */
  public static String getExtClass() {

    return extClass;
  }

  /**
   * @param extClass new value of {@link #getExtClass}.
   */
  public static void setExtClass(String extClass) {

    SecureLogging.extClass = extClass;
  }

  /**
   * @return methodName
   */
  public static String getMethodName() {

    return methodName;
  }

  /**
   * @param methodName new value of {@link #getMethodName}.
   */
  public static void setMethodName(String methodName) {

    SecureLogging.methodName = methodName;
  }

  /**
   * @return methodParam
   */
  public static String getMethodParam() {

    return methodParam;
  }

  /**
   * @param methodParam new value of {@link #getMethodParam}.
   */
  public static void setMethodParam(String methodParam) {

    SecureLogging.methodParam = methodParam;
  }

}
