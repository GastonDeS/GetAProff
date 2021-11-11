package ar.edu.itba.paw.services;

//@Service
public class RoleServiceImpl  {

//    @Autowired
//    private RoleDao roleDao;
//
//    @Override
//    public Role create(String role) {
//        return roleDao.create(role);
//    }
//
//    @Override
//    public Optional<Role> findRoleByName(String role) {
//        return roleDao.findRoleByName(role);
//    }
//
//    @Override
//    public List<Role> getUserRoles(Long userid) {
//        //TODO: BORRAR PORQUE YA NO SIRVE
//        return roleDao.getUserRoles(userid);
//    }
//
//    @Transactional
//    @Override
//    public List<Role> setUserRoles(Long userId, Long userRole) {
//        List<Role> userRoles = new ArrayList<>();
//        if (Objects.equals(userRole, Roles.TEACHER.id)) {
//            addRoleToList(userRoles, Roles.TEACHER);
//        }
//        addRoleToList(userRoles, Roles.STUDENT);
//        return userRoles.isEmpty() ? new ArrayList<>() : userRoles;
//    }
//
//    @Transactional
//    @Override
//    public Boolean addTeacherRole(Long userId) {
//        Optional<Role> teacherRole = findRoleByName(Roles.TEACHER.name);
//        if (teacherRole.isPresent()) {
//            return roleDao.addRoleToUser(teacherRole.get().getRoleId(), userId);
//        }
//        return false;
//    }
//
//    private void addRoleToList(List<Role> userRoles, Roles role) {
//        Optional<Role> newRole = findRoleByName(role.name);
//        newRole.ifPresent(userRoles::add);
//    }
//
//    private enum Roles {
//        STUDENT("USER_STUDENT", 0L),
//        TEACHER("USER_TEACHER", 1L);
//
//        private String name;
//        private Long id;
//
//        Roles(String name, Long id) {
//            this.name = name;
//            this.id = id;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public Long getId() {
//            return id;
//        }
//    }
}
