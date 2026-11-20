package group.networkinventorytask.company.inventory.dto.response;

public record UserResponse(
        String username,
        boolean enabled,
        String role
) {
}