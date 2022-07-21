### BeanPostProcessor

- BeanPostProcessor 接口及其子接口

-----



#### BeanPostProcessor 接口及其子接口

```shell
├── BeanPostProcessor
    |	#Object postProcessBeforeInitialization(Object bean, String beanName)
    |   #Object postProcessAfterInitialization(Object bean, String beanName)
    |
    └── DestructionAwareBeanPostProcessor
    |       #void postProcessBeforeDestruction(Object bean, String beanName)
    |       #boolean requiresDestruction(Object bean)
    |
    └── MergedBeanDefinitionPostProcessor
    |       #void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName)
    |       #void resetBeanDefinition(String beanName)
    |
    └── InstantiationAwareBeanPostProcessor
    	|   #Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName)
        |	#boolean postProcessAfterInstantiation(Object bean, String beanName)
        |	#PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName)
        |
        └── SmartInstantiationAwareBeanPostProcessor
                #Class<?> predictBeanType(Class<?> beanClass, String beanName)
                #Constructor<?>[] determineCandidateConstructors(Class<?> beanClass, String beanName)
                #Object getEarlyBeanReference(Object bean, String beanName)

```

