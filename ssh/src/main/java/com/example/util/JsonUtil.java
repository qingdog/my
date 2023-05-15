package com.example.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.book.domain.UserDetails;
import org.apache.commons.logging.Log;
import org.springframework.util.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @version 0.2
 * @apiNote 注意测试
 */
public class JsonUtil {
    //	private Class<?> clazz = this.getClass();
    private static final Log log = org.apache.commons.logging.LogFactory.getLog(JsonUtil.class);
    // 直接存储对象引用，哈希码第一次获取后生成固定字符串保存到对象头中
    private final List<HashMap<Object, Integer>> recursionObject = new ArrayList<>();
    //	private static final HashSet<String> EMPTY_EXCLUDNAME;
//	static {
//		EMPTY_EXCLUDNAME = new HashSet<String>();
//	}
    private final HashSet<String> excludeFieldNames = new HashSet<>();
    // 按照字符串类名进行排除字段名集合
    private final HashMap<Class<?>, List<String>> excludeClassFieldNames = new HashMap<>();
    // 自定义类型定制器作为属性存入工具类中。只能放一个作用不大，待处理
    List<JsonUtilClassCustomizer<?>> classCustomizerList = null;
    private int cycleTimes = 0; // 自循环次数
    private int recursionTimes = 4; // 递归深度
    private String dateFormat = "yyyy-MM-dd HH:mm";

    public JsonUtil() {
//        this.setCycleTimes(1);
        // EnhancerByCGLIB
        this.excludeFieldNames.add("hibernateLazyInitializer");
        this.excludeFieldNames.add("interceptFieldCallback");
        // Property '" + key + "' of " + bean.getClass() + " has no read method. SKIPPED
        /*this.excludeFieldNames.add("latelyNames");
        this.excludeFieldNames.add("leader");
        this.excludeFieldNames.add("myLeader");
        // com.landray.kmss.sys.right.interfaces.ExtendAuthModel
        String[] extendAuthModelFieldNames = new String[]{"attachmentForms", "authAllEditors", "authAllReaders",
                "authArea", "authAttCopys", "authAttDownloads", "authAttNocopy", "authAttNodownload", "authAttNoprint",
                "authAttPrints", "authChangeAtt", "authChangeEditorFlag", "authChangeReaderFlag", "authEditorFlag",
                "authEditors", "authOtherEditors", "authOtherReaders", "authRBPFlag", "authReaderFlag", "authReaders",
                "customPropMap", "dynamicMap", "mechanismMap", "sysDictModel", "toFormPropertyMap"};
        this.excludeFieldNames.addAll(Arrays.asList(extendAuthModelFieldNames));
        // com.landray.kmss.sys.organization.model.SysOrgElement
//		this.excludeFieldNames.add("fdChildren");
//		this.excludeFieldNames.add("fdParent");
        String[] sysOrgElementFieldNames = new String[]{"addressTypeList", "allLeader", "allMyLeader",
                "authElementAdmins", "fdGroups", "fdInitPassword", "fdParentOrg", "fdPassword", "fdPersons", "fdPosts",
                "hbmChildren", "hbmGroups", "hbmParent", "hbmParentOrg", "hbmPersons", "hbmPosts", "hbmSuperLeader",
                "hbmSuperLeaderChildren", "hbmThisLeader", "hbmThisLeaderChildren", "docCreator", "docAlteror"};
        this.excludeFieldNames.addAll(Arrays.asList(sysOrgElementFieldNames));
        // com.landray.kmss.sys.simplecategory.model.SysSimpleCategoryAuthTmpModel
        String[] sysSimpleCategoryAuthTmpModelFieldNames = new String[]{"authNotReaderFlag", "authTmpAttCopys",
                "authTmpAttDownloads", "authTmpAttNocopy", "authTmpAttNocopy", "authTmpAttNodownload",
                "authTmpAttNoprint", "authTmpAttPrints", "authTmpEditors", "authTmpReaders"};
        this.excludeFieldNames.addAll(Arrays.asList(sysSimpleCategoryAuthTmpModelFieldNames));
        // list - main
//		this.excludeFieldNames.add("docMain");
//		this.excludeFieldNames.add("docTemplate");
        this.excludeFieldNames.add("extendDataModelInfo");
        this.excludeFieldNames.add("extendDataXML");
        // fdParent - fdChildren
//		this.excludeFieldNames.add("fdChildren");*/
    }

    public static void main(String[] args) {
        System.out.println(new JsonUtil().beanToJson(new JsonUtil()));
    }

//	public interface JsonUtilClassCustomizer<T> {
//		public abstract JSONObject getValue(T object);
//	}
//	Class<?> jsonUtilClassCustomizer = JsonUtilClassCustomizer.class;

    /**
     * javaBean对象转换为json对象
     *
     * @param bean JavaBean
     * @return json
     */
    public JSONObject beanToJson(Object bean) {
        final JsonUtil jsonUtil = this;

		/*return beanToJson(bean, this.new JsonUtilClassCustomizer<com.landray.kmss.sys.organization.model.SysOrgElement>() {
			@Override
			public JSONObject getValue(com.landray.kmss.sys.organization.model.SysOrgElement object) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("fdId", object.getFdId());
				jsonObject.put("fdName", object.getFdName());
				jsonObject.put("fdNo", object.getFdNo());
				if (object.getFdParent() != null) {
					Object value = jsonUtil.getValue(object.getFdParent(), jsonUtil.getRecursionTimesValue());
					jsonObject.put("fdParent", value);
				}
				return jsonObject;
			}
		});*/
        return beanToJson(bean, null);
    }
	
	/*public JSONObject parse(Object bean) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "handler", "hibernateLazyInitializer", "interceptFieldCallback" });
//		jsonConfig.setIgnoreDefaultExcludes(false); // 设置默认忽略
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
//		jsonConfig.setIgnoreTransientFields(false);
		return JSONObject.fromObject(bean, jsonConfig);
	}*/

    public JSONObject beanToJson(Object bean, JsonUtilClassCustomizer<?>... classCustomizers) {
        if (bean == null) return null;

        /*if (classCustomizers == null) {
            classCustomizers = new JsonUtilClassCustomizer<T>() {
                @Override
                public JSONObject getValue(T object) {
                    return null;
                }
            };
        }*/
        if (classCustomizers == null) {
            this.classCustomizerList = Collections.singletonList(new JsonUtil.JsonUtilClassCustomizer<Object>() {
                @Override
                public JSONObject getValue(Object object) {
                    return null;
                }
            });
        } else this.classCustomizerList = Arrays.asList(classCustomizers);

		/*Type[] genericInterfaces = classCustomizer.getClass().getGenericInterfaces();
		if (genericInterfaces.length > 0) {
			Type type = genericInterfaces[0];
			if (type instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) type;
				Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
				if (actualTypeArguments.length > 0) {
					Type actualTypeArgument = actualTypeArguments[0];
					if (actualTypeArgument instanceof Class) {
						this.jsonUtilClassCustomizer = (Class<?>) actualTypeArgument;
					}
				}
			}
		}*/
        // class
        this.excludeFieldNames.add("class");
        this.excludeFieldNames.add("formClass");

//		JSONObject jsonObject = new JSONObject();
//		PropertyDescriptor[] propertyDescriptors = org.apache.commons.beanutils.PropertyUtils.getPropertyDescriptors(bean);
//		for (PropertyDescriptor propertyDescriptor: propertyDescriptors) {
//			this.oneBreadthIsRecursion = false;
//			this.setJsonObject(jsonObject, bean, propertyDescriptor, this.recursionTimes);
//			// 当前对象递归结束
//			if(this.oneBreadthIsRecursion) {
//				this.oneDepthAllRecursionObject.clear();
//			}
//		}
//		return jsonObject;

        for (int i = 0; i <= this.recursionTimes; i++) {
            HashMap<Object, Integer> set = new HashMap<>();
            this.recursionObject.add(set);
        }
        // 初始对象始终保留不再递归出该对象的重复数据加入json中
        HashMap<Object, Integer> set = new HashMap<>();
        set.put(bean.hashCode(), this.cycleTimes);
        this.recursionObject.add(set);

        // 排除示例：
        this.excludeClassFieldNames.put(bean.getClass(), new ArrayList<>(Collections.singletonList("class")));
        return parseBean(bean, this.recursionTimes);
    }

    /**
     * 解析JavaBean
     *
     * @param bean           javabean对象
     * @param recursionTimes 递归深度倒数次数
     * @return 返回json
     */
    public JSONObject parseBean(Object bean, int recursionTimes) {
//		this.oneBreadthIsRecursion = true;
        JSONObject jsonObject = new JSONObject();

        PropertyDescriptor[] propertyDescriptors = org.apache.commons.beanutils.PropertyUtils.getPropertyDescriptors(bean);
        // version 0.2
        List<?> objects = this.excludeClassFieldNames.get(bean.getClass());
        if (!CollectionUtils.isEmpty(objects)) {
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                if (!objects.contains(propertyDescriptor.getName())) {
                    this.setJsonObject(jsonObject, propertyDescriptor, bean, recursionTimes);
                }
            }
            return jsonObject;
        }

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            this.setJsonObject(jsonObject, propertyDescriptor, bean, recursionTimes);
        }
        return jsonObject;
    }

    private void setJsonObject(JSONObject jsonObject, PropertyDescriptor propertyDescriptor, Object bean, int recursionTimes) {
        String name = propertyDescriptor.getName();
        // 通过哈希函数快速地对数据元素进行定位，注意全类匹配可能过大
        if (this.excludeFieldNames.contains(name)) {
            log.info("属性：" + name + '\t' + bean.getClass());
            return;
        }
        Method readMethod = propertyDescriptor.getReadMethod();
        if (readMethod != null) {
            Object property = null;
            try {
                property = org.apache.commons.beanutils.PropertyUtils.getProperty(bean, name);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            Object value = this.getValue(property, recursionTimes);
            // key为null时，net.sf.json.JSONObject无法存入，修改为"null"。value为null时，修改为空仍然存放到jsonObject
            jsonObject.put(name == null ? "null" : name, value == null ? "" : value);
        } else {
            // this.excludeFieldNames.add(name); 修改为更细粒度
            List<String> objects = this.excludeClassFieldNames.get(bean.getClass());
            if (objects == null) {
                objects = new ArrayList<>(Collections.singletonList(name));
            } else {
                objects.add("name");
            }
            this.excludeClassFieldNames.put(bean.getClass(), objects);
            log.warn("Property '" + name + "' of " + bean.getClass() + " has no read method. SKIPPED");
        }

    }

    /**
     * 获取字段的值
     *
     * @param object         字段对象
     * @param recursionTimes 递归次数
     */
    public Object getValue(Object object, int recursionTimes) {
        // JSONObject中放置Map的时候，会自动将Map看成是JSONObject来处理。JSON allows only string to be a key
//		if(recursionTimes <= 0)
//			return object.getClass();
//		--recursionTimes;
        Object value;
        if (object == null || object instanceof String || object instanceof Number || object instanceof Boolean) {
            value = object;
        } else if (object instanceof Date) {
            SimpleDateFormat sdf = new SimpleDateFormat(this.dateFormat);
            value = sdf.format(object);
        } else if (Collection.class.isAssignableFrom(object.getClass())) {
            JSONArray jsonArray = new JSONArray();

            Collection<?> collection = (Collection<?>) object;
            for (Object next : collection) {
                // 限制过多对象集合
//            	Class<?> superclass = next.getClass().getSuperclass();
//            	Integer times = this.recursionClassTimes.get(superclass.getName());
//        		if(times != null)
//        			--recursionTimes;
                Object val = this.getValue(next, recursionTimes);
                jsonArray.add(val);
            }
            value = jsonArray;
        } else if (Map.class.isAssignableFrom(object.getClass())) {
            JSONObject jsonObject = new JSONObject();
            Map<?, ?> map = ((Map<?, ?>) object);
            try {
                for (Object key : map.keySet()) {
                    Object obj = map.get(key);
                    Object val = this.getValue(obj, recursionTimes);

                    jsonObject.put(key == null ? "null" : key.toString(), val == null ? "" : val);
                }
            } catch (UnsupportedOperationException e) {
                e.printStackTrace();
            }
            value = jsonObject;
        } else if (object.getClass().isArray()) {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < Array.getLength(object); i++) {
                Object obj = Array.get(object, i);
                Object val = this.getValue(obj, recursionTimes);
                jsonArray.add(val);
            }
            value = jsonArray;
        } else {
            // 暂时不考虑无限制
            if (recursionTimes <= 0) {
                return object.getClass() + " depth overflow!";
            }
            // 不再受次数限制
//			if (this.clazz.isInstance(object)) {
            Optional<JSONObject> first = this.classCustomizerList.stream()
                    .map(classCustomizer -> classCustomizer.getClazzValue(object))
                    .filter(Objects::nonNull).findFirst();
            // 重写抽象方法结果为null后不返回继续解析
            if (first.isPresent()) {
                return first.get();
            }

            // 清除该递归次数中的所有对象
            for (int i = recursionTimes; i > 0; i--) {
                HashMap<Object, Integer> hashMap = this.recursionObject.get(i);
                if (hashMap.isEmpty()) {
                    break;
                } else {
                    hashMap.clear();
                }
            }

            // 两个对象是包含的关系
//			int hashCode = object.hashCode();
            for (int i = recursionTimes + 1; i <= this.recursionTimes + 1; i++) {
                HashMap<Object, Integer> set = this.recursionObject.get(i);
                // 先比较哈希码
//				Object oldObject = hashMap.get(hashCode);
//				if (oldObject != null) {
                // 如果出现自循环引用将无法正确解析，比如张三的属性修改人是张三，张三的属性parent组员是李四，李四的children组员(或修改人)是张三（该对象张三无法将会被错误忽略）
                // 改进方向 -> 存储对象引用同时保存次数一直到两次。HashMap(Object, Integer)
                if (set.containsKey(object)) {
                    /*
                     * 此方法只比较public字段。
                     * 不比较transient字段，因为它们不能被序列化。
                     * 此外，此方法不比较static字段，因为它们不是对象实例的一部分。
                     * 如果某个字段是一个数组/Map/Collection，则比较内容，而不是对象的引用。
                     * @param excludeFields  不比较的字段
                     */
					/*if (org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals(oldObject, object)) {
					} else {
						log.warn(object.getClass() + "：" + object.toString() + " 字段不相等！");
					}*/
                    // 定制BaseModel对象
					/*if (com.landray.kmss.common.model.BaseModel.class.isAssignableFrom(object.getClass())) {
						String fdId = ((com.landray.kmss.common.model.BaseModel) object).getFdId();
						JSONObject json = new JSONObject();
						json.put("fdId", fdId);
						return json;
					}*/
                    // 相同对象返回null
                    if (set.get(object) <= 0) {
                        return object.getClass() + " is a cycle!";
                    } else {
                        set.put(object, set.get(object) - 1);
                    }
                }
            }
            HashMap<Object, Integer> set = this.recursionObject.get(recursionTimes);
            set.put(object, this.cycleTimes);
            // 再次解析并且递归次数减一
            // JSONObject objectValue = getMethodValue(object, --recursionTimes);
            value = this.parseBean(object, --recursionTimes);
        }
        return value;
    }

    public void setRecursionTimes(int recursionTimes) {
        this.recursionTimes = recursionTimes;
    }

    public int getRecursionTimesValue() {
        return --recursionTimes;
    }

    public void addExcludeFieldNames(List<String> excludeFieldNames) {
        this.excludeFieldNames.addAll(excludeFieldNames);
    }

    public boolean removeExcludeFieldNames(String fieldName) {
        return excludeFieldNames.remove(fieldName);
    }

    public void clearExcludeField() {
        excludeFieldNames.clear();
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public JsonUtil setExcludeClassFieldNames(Class<?> clazz, List<String> excludeFieldNames) {
        this.excludeClassFieldNames.put(clazz, excludeFieldNames);
        return this;
    }

    public void setCycleTimes(int cycleTimes) {
        this.cycleTimes = cycleTimes;
    }

    public abstract static class JsonUtilClassCustomizer<T> {
        Class<?> clazz = this.getClass();

        // 使用抽象类通过匿名内部类拿到泛型
        public JsonUtilClassCustomizer() {
            Type type = this.getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
                if (actualTypeArguments.length > 0) {
                    Type actualTypeArgument = actualTypeArguments[0];
                    if (actualTypeArgument instanceof Class) {
                        this.clazz = (Class<?>) actualTypeArgument;
                    }
                }
            }
        }

        public abstract JSONObject getValue(T object);

        public boolean isClazz(Object object) {
            return this.clazz.isInstance(object);
        }

        public JSONObject getClazzValue(Object object) {
            if (this.isClazz(object)) {
                return this.getValue((T) object);
            }
            return null;
        }
    }
	/*class old {
		public <T> boolean isEquals(Object oldObject, Object newObject, String... ignoreProperties) {
			boolean isEquals = true;
			if (oldObject != null && newObject != null) {
				// 如果没有重写equals方法将会判断两个对象内存地址是否一致即oldObject==newObject
				if (oldObject.equals(newObject)) {
					return true;
				}

				Map<String, Object> oldObjectPropertyMap = this.getObjectPropertyMap(oldObject);
				Map<String, Object> newObjectPropertyMap = this.getObjectPropertyMap(newObject);

				for (String key : oldObjectPropertyMap.keySet()) {
					Object oldObjectPropertyValue = oldObjectPropertyMap.get(key);
					Object newObjectPropertyValue = newObjectPropertyMap.get(key);
					if (newObjectPropertyValue != null && oldObjectPropertyValue != null) {
						if (!(oldObjectPropertyValue.equals(newObjectPropertyValue))) {
							if (Collection.class.isAssignableFrom(oldObjectPropertyValue.getClass())) {

							} else if (com.landray.kmss.common.model.BaseModel.class
									.isAssignableFrom(oldObjectPropertyValue.getClass())) {

							} else {
								isEquals = false;
								break;
							}
						}
						// 两个对象都指向null
					} else if (oldObjectPropertyValue != newObjectPropertyValue) {
						isEquals = false;
						break;
					}
				}
			} else if (oldObject != newObject) {
				isEquals = false;
			}
			return isEquals;
		}

		// 获取对象属性名字和数值
		public <T> Map<String, Object> getObjectPropertyMap(Object object, String... ignoreProperties) {
			Class<?> objectClass = object.getClass();
			PropertyDescriptor[] propertyDescriptors = org.apache.commons.beanutils.PropertyUtils.getPropertyDescriptors(objectClass);

			HashMap<String, Object> propertyMap = new HashMap<>(propertyDescriptors.length);

			Collection<?> ignorePropertyCollection = null;
			if (ignoreProperties != null) {
				ignorePropertyCollection = Arrays.asList(ignoreProperties);
			} else {
//				ignorePropertyCollection = this.excludeFieldNames;
			}
			if (ignorePropertyCollection.isEmpty()) {
				for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
					String name = propertyDescriptor.getName();
					Method readMethod = propertyDescriptor.getReadMethod();
					if (readMethod != null) {
						Object invoke = null;
						try {
							invoke = readMethod.invoke(object);
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							e.printStackTrace();
						}
						propertyMap.put(name, invoke);
					}
				}
			} else {
				for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
					String name = propertyDescriptor.getName();
					Method readMethod = propertyDescriptor.getReadMethod();
					if (readMethod != null && ignorePropertyCollection.contains(name)) {
						Object invoke = null;
						try {
							invoke = readMethod.invoke(object);
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							e.printStackTrace();
						}
						propertyMap.put(name, invoke);
					}
				}
			}
			return propertyMap;
		}

		@SuppressWarnings("unused")
		private JSONObject parseForDeclaredField(Object bean, int recursionTimes) {
			JSONObject jsonObject = new JSONObject();
			// 通过getDeclaredFields()?法获取对象类中的所有属性（含私有）
			java.lang.reflect.Field[] fields = bean.getClass().getDeclaredFields();

			for (java.lang.reflect.Field field : fields) {
				// 设置允许通过反射访问私有变量
				// if (Modifier.isPrivate(field.getModifiers()))
				field.setAccessible(true);
				// 获取字段属性名称
				String name = field.getName();
				Object object = null;
				try {
					object = field.get(bean);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}

				if (object != null && name != null && name != "") {
//					Object value = getValue(object, recursionTimes);
//					jsonObject.put(name, value);
				} else {
					// 避免没值而导致前端undefined
					jsonObject.put(name, "");
				}
			}
			return jsonObject;
		}

		@SuppressWarnings("unused")
		private JSONObject getMethodValue(Object object, int recursionTimes) {
			JSONObject jsonObject = new JSONObject();

			Method[] methods = object.getClass().getMethods();
			for (Method method : methods) {
				String name = method.getName();
				Class<?>[] parameterTypes = method.getParameterTypes();
				if (name.contains("getFd") && parameterTypes.length == 0) {
					Object invoke = null;
					try {
						invoke = method.invoke(object);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
					if (invoke != null && !(invoke instanceof Method)) {
						int indexOf = name.indexOf("getF");
						if (indexOf != -1) {
							String substring = name.substring(indexOf + "getF".length(), name.length());
							if (substring != null && substring != "") {
//								Object value = getValue(invoke, recursionTimes);
//								jsonObject.put('f' + substring, value);
							}
						}
					}
				}
			}
			return jsonObject;
		}

		@SuppressWarnings("unused")
		private JSONObject getCGLIBValue(Object bean1) {
			JSONObject jsonObj = new JSONObject();
			Field[] fields = bean1.getClass().getDeclaredFields();
			for (java.lang.reflect.Field field : fields) {
				field.setAccessible(true);
				Object object1 = null;
				try {
					object1 = field.get(bean1);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				String name1 = field.getName();
				if (object1 != null && name1 != null && name1 != "") {
					if (object1 instanceof Method) {
						// 对象的组织架构对象调用get方法取值
						if (name1.contains("getFd")) {
							Method method = (Method) object1;
							Class<?>[] parameterTypes = method.getParameterTypes();
							// 没有参数调用
							if (parameterTypes.length == 0) {
								Object invoke = null;
								try {
									invoke = method.invoke(bean1);
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									e.printStackTrace();
								}
								if (invoke != null && !(invoke instanceof Method)) {
									int indexOf = name1.indexOf("CGLIB$getF");
									int lastIndexOf = name1.lastIndexOf("$");
									if (indexOf != -1 && lastIndexOf != -1 && lastIndexOf > indexOf) {
										String substring = name1.substring(indexOf + "CGLIB$getF".length(), lastIndexOf);
										if (substring != null && substring != "") {
											jsonObj.put('f' + substring, invoke.toString());
										}
									}
								}
							}
						}
					} else {

					}
				} else {
					jsonObj.put(name1, "");
				}
			}
			return jsonObj;
		}
	}*/
}
