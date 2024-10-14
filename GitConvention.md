# 📋 Git Convention

---

## Commit Rule

### template
```
[{type}]: {subject}  -- 헤더(필수)
[description]
{content}            -- 본문(필수)
```

### type
```
feat : new feature
fix : bug fix
build : build, dependency
chore : etc 
ci : ci/cd 
docs : document 
style : code style formatting 
refactor : code refactoring 
test : test code
```

### example
```
[feat]: 푸시 메세지 기능 구현
[description]
- 푸시 메세지 기능 구현
```

---

## Branch Rule

### Branch List
```
release
staging
feature
```

### Branch Life Cycle
```
planning -> design -> issue -> feature -> feature-{issue} -> staging -> release -> remove: feature-{issue}

release -> critical -> hotfix -> release -> remove: hotfix
```