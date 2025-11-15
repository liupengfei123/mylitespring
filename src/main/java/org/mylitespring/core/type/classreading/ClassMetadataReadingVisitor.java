package org.mylitespring.core.type.classreading;

import org.mylitespring.core.type.ClassMetadata;
import org.mylitespring.util.ClassUtils;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.Opcodes;
import org.springframework.asm.SpringAsmInfo;

public class ClassMetadataReadingVisitor extends ClassVisitor implements ClassMetadata {

    private String className;

    private boolean isInterface;

    private boolean isAbstract;

    private boolean isFinal;

    private String superClassName;

    private String[] interfaces;


    public ClassMetadataReadingVisitor() {
        super(SpringAsmInfo.ASM_VERSION);
    }


    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);

        this.className = ClassUtils.convertResourcePathToClassName(name);

        this.isInterface = (access & Opcodes.ACC_INTERFACE) == Opcodes.ACC_INTERFACE;
        this.isAbstract = (access & Opcodes.ACC_ABSTRACT) == Opcodes.ACC_ABSTRACT;
        this.isFinal = (access & Opcodes.ACC_FINAL) == Opcodes.ACC_FINAL;

        if (superName != null) {
            this.superClassName = ClassUtils.convertResourcePathToClassName(superName);
        }

        this.interfaces = new String[interfaces.length];
        for (int i = 0; i < interfaces.length; i++) {
            this.interfaces[i] = ClassUtils.convertResourcePathToClassName(interfaces[i]);
        }
    }

    @Override
    public boolean isAbstract() {
        return isAbstract;
    }

    @Override
    public boolean isInterface() {
        return isInterface;
    }

    @Override
    public boolean isFinal() {
        return isFinal;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public boolean hasSuperClass() {
        return (superClassName != null);
    }

    @Override
    public String getSuperClassName() {
        return superClassName;
    }

    @Override
    public String[] getInterfaceNames() {
        return interfaces;
    }
}
